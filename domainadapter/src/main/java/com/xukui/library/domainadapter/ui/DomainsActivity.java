package com.xukui.library.domainadapter.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.xukui.library.domainadapter.DomainManager;
import com.xukui.library.domainadapter.R;
import com.xukui.library.domainadapter.adapter.DomainsRecyclerAdapter;
import com.xukui.library.domainadapter.bean.DomainTemplate;
import com.xukui.library.domainadapter.bean.KeyValue;
import com.xukui.library.domainadapter.store.DomainStore;
import com.xukui.library.domainadapter.util.LogUtil;
import com.xukui.library.domainadapter.util.ToastUtil;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DomainsActivity extends AppCompatActivity {

    private RecyclerView recycler_domain;

    private DomainsRecyclerAdapter mRecyclerAdapter;

    private Dialog mTemplateDialog;
    private int mTemplateIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.domainadapter_activity_domains);
        initData();
        initView();
        setView();
        loadData();
    }

    private void initData() {
        mRecyclerAdapter = new DomainsRecyclerAdapter();
    }

    private void initView() {
        recycler_domain = findViewById(R.id.recycler_domain);
    }

    private void setView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler_domain.setLayoutManager(layoutManager);
        recycler_domain.setAdapter(mRecyclerAdapter);
    }

    private void loadData() {
        List<KeyValue> list = DomainStore.getInstance().getAll();
        mRecyclerAdapter.setNewData(list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.domainadapter_menu_domains, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_template) {
            showTemplateDialog();

        } else if (item.getItemId() == R.id.action_save) {
            List<KeyValue> actualList = mRecyclerAdapter.getActualData();

            if (actualList != null) {
                boolean success = DomainStore.getInstance().putAll(actualList);

                if (success) {
                    if (DomainManager.isLogEnabled()) {
                        String des = getDomainListDes(actualList);
                        LogUtil.d("域名保存成功, 详情:\n" + (TextUtils.isEmpty(des) ? "无" : des));
                    }

                    ToastUtil.showShort("保存成功, 请重启后再试!");

                } else {
                    ToastUtil.showShort("保存失败");
                }
            }
        }

        return true;
    }

    private String getDomainListDes(List<KeyValue> list) {
        StringBuffer sb = new StringBuffer();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                KeyValue keyValue = list.get(i);
                sb.append((i + 1) + " " + keyValue.toString() + "\n");
            }
        }
        return sb.toString();
    }

    private void showTemplateDialog() {
        if (mTemplateDialog == null) {
            List<DomainTemplate> templates = DomainManager.getDomainTemplates();
            String[] templateNames = new String[templates.size()];

            for (int i = 0; i < templates.size(); i++) {
                DomainTemplate template = templates.get(i);
                templateNames[i] = template.getName();
            }

            mTemplateDialog = new AlertDialog.Builder(this)
                    .setTitle("选择模板")
                    .setSingleChoiceItems(templateNames, mTemplateIndex, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mTemplateIndex = which;
                        }
                    })
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DomainTemplate template = DomainManager.getDomainTemplate(mTemplateIndex);

                            if (template != null) {
                                List<KeyValue> list = DomainStore.convertMapToList(template.getMap());
                                mRecyclerAdapter.setNewData(list);
                            }
                        }
                    })
                    .create();
        }

        mTemplateDialog.show();
    }

}