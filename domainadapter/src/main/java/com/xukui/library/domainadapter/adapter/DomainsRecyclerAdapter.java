package com.xukui.library.domainadapter.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.xukui.library.domainadapter.R;
import com.xukui.library.domainadapter.bean.KeyValue;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class DomainsRecyclerAdapter extends RecyclerView.Adapter<DomainsRecyclerAdapter.ViewHolder> {

    private List<KeyValue> mList;
    private List<KeyValue> mActualList;

    private LayoutInflater mInflater;

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(viewGroup.getContext());
        }

        View view = mInflater.inflate(R.layout.domainadapter_item_recycler_domains, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        KeyValue keyValue = mList.get(position);

        viewHolder.input_value.setHint(keyValue.getKey());

        EditTextWatcher textWatcher = (EditTextWatcher) viewHolder.et_value.getTag();
        if (textWatcher != null) {
            viewHolder.et_value.removeTextChangedListener(textWatcher);
        }
        textWatcher = new EditTextWatcher(position);
        viewHolder.et_value.addTextChangedListener(textWatcher);
        viewHolder.et_value.setTag(textWatcher);
        viewHolder.et_value.setHint(keyValue.getKey());
        viewHolder.et_value.setText(keyValue.getValue());
    }

    private class EditTextWatcher implements TextWatcher {

        private int mPosition;

        public EditTextWatcher(int position) {
            mPosition = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mActualList.get(mPosition).setValue(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

    }

    public void setNewData(List<KeyValue> list) {
        this.mList = list;

        this.mActualList = new ArrayList<>();
        if (list != null) {
            try {
                for (KeyValue keyValue : list) {
                    this.mActualList.add(keyValue.clone());
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        notifyDataSetChanged();
    }

    @Nullable
    public List<KeyValue> getActualData() {
        return this.mActualList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextInputLayout input_value;
        EditText et_value;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            input_value = itemView.findViewById(R.id.input_value);
            et_value = itemView.findViewById(R.id.et_value);
        }

    }

}