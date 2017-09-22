package com.rumofuture.nemo.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rumofuture.nemo.R;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.view.activity.MyBlogActivity;
import com.rumofuture.nemo.view.activity.MyBookListActivity;
import com.rumofuture.nemo.view.activity.MyFavoriteBookListActivity;
import com.rumofuture.nemo.view.activity.MyFollowAuthorListActivity;
import com.rumofuture.nemo.view.activity.MyFollowerListActivity;
import com.rumofuture.nemo.view.activity.MySettingListActivity;

import cn.bmob.v3.BmobUser;

public class NemoMainMeFragment extends Fragment {

    private ImageView mMyAvatarView;
    private TextView mMyNameView;
    private TextView mMyMottoView;
    private TextView mMyFollowAuthorTotalView;
    private TextView mMyFollowerTotalView;
    private TextView mMyFavoriteBookView;

    public NemoMainMeFragment() {

    }

    public static NemoMainMeFragment newInstance() {
        return new NemoMainMeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_main_me, container, false);

        LinearLayout myInfoContainer = (LinearLayout) view.findViewById(R.id.my_info_container);
        myInfoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyBlogActivity.actionStart(getActivity());
            }
        });

        mMyAvatarView = (ImageView) view.findViewById(R.id.my_avatar_view);
        mMyNameView = (TextView) view.findViewById(R.id.my_name_view);
        mMyMottoView = (TextView) view.findViewById(R.id.my_motto_view);
        mMyFollowAuthorTotalView = (TextView) view.findViewById(R.id.my_follow_author_total_view);
        mMyFollowerTotalView = (TextView) view.findViewById(R.id.my_follower_total_view);
        mMyFavoriteBookView = (TextView) view.findViewById(R.id.my_favorite_book_total_view);

        TextView myBookListView = (TextView) view.findViewById(R.id.my_book_list_view);
        myBookListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyBookListActivity.actionStart(getActivity());
            }
        });

        TextView myFollowAuthorListView = (TextView) view.findViewById(R.id.my_follow_author_list_view);
        myFollowAuthorListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFollowAuthorListActivity.actionStart(getActivity());
            }
        });

        TextView myFollowerListView = (TextView) view.findViewById(R.id.my_follower_list_view);
        myFollowerListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFollowerListActivity.actionStart(getActivity());
            }
        });

        TextView myFavoriteBookListView = (TextView) view.findViewById(R.id.recycler_view);
        myFavoriteBookListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFavoriteBookListActivity.actionStart(getActivity());
            }
        });

        TextView mySettingListView = (TextView) view.findViewById(R.id.my_setting_list_view);
        mySettingListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySettingListActivity.actionStart(getActivity());
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        User myself = BmobUser.getCurrentUser(User.class);
        if (null != myself.getAvatar()) {
            Glide.with(getActivity()).load(myself.getAvatar().getUrl()).into(mMyAvatarView);
        }
        mMyNameView.setText(myself.getName());
        mMyMottoView.setText(myself.getMotto());
        mMyFollowAuthorTotalView.setText(String.valueOf(myself.getFollowTotal()));
        mMyFollowerTotalView.setText(String.valueOf(myself.getFollowerTotal()));
        mMyFavoriteBookView.setText(String.valueOf(myself.getFavoriteTotal()));
    }
}
