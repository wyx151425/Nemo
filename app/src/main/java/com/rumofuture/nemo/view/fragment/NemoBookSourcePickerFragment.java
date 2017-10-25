package com.rumofuture.nemo.view.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.rumofuture.nemo.R;

public class NemoBookSourcePickerFragment extends DialogFragment {

    public NemoBookSourcePickerFragment() {

    }

    public static NemoBookSourcePickerFragment newInstance() {
        return new NemoBookSourcePickerFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_nemo_list_view, null);
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        final String[] sourceArray = {"创建", "分享"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(), R.layout.item_nemo_profession_list, sourceArray);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getActivity(), sourceArray[position], Toast.LENGTH_SHORT).show();
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.prompt_book_source_pick)
                .setNegativeButton(R.string.prompt_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
    }
}
