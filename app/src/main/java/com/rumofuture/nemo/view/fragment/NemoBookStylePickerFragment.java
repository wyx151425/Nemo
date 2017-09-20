package com.rumofuture.nemo.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rumofuture.nemo.R;

public class NemoBookStylePickerFragment extends DialogFragment {

    public static final String EXTRA_STYLE = "com.rumofuture.nemo.view.fragment.NemoBookStylePickerFragment.mStyle";

    public String mStyle;
    public View mPreviousFocusedView;

    public String[] mStyleArray = {"古典", "热血", "科幻", "推理", "爆笑", "萌系", "唯美", "悬疑"};

    public NemoBookStylePickerFragment() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_nemo_book_style_picker, null);

        final ListView bookStyleListView = (ListView) view.findViewById(R.id.album_list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(), R.layout.item_nemo_book_style_list, mStyleArray);
        bookStyleListView.setAdapter(adapter);
        bookStyleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mPreviousFocusedView != null)
                    mPreviousFocusedView.setBackgroundColor(0);
                view.setBackgroundColor(getResources().getColor(R.color.colorGainsboro));
                mStyle = mStyleArray[i];
                mPreviousFocusedView = view;
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.prompt_book_style_pick)
                .setPositiveButton(R.string.prompt_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResult(Activity.RESULT_OK, mStyle);
                    }
                })
                .setNegativeButton(R.string.prompt_cancel, null)
                .create();
    }

    private void sendResult(int resultCode, String style) {
        if (getTargetFragment() == null)
            return;

        Intent intent = new Intent();
        intent.putExtra(EXTRA_STYLE, style);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}
