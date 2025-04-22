package com.example.spotify_wrapper20.ui.AccountEdit;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.spotify_wrapper20.R;

public class AccountEditFragment extends Fragment {

    private AccountEditViewModel mViewModel;

    public static AccountEditFragment newInstance() {
        return new AccountEditFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("AccountEdit", "inside AccountEdit fragment: ");
        Log.d("AccountEditFragment", "inside AccountEdit fragment: ");
        Toast.makeText(requireContext(), "inside account edit fragment", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_account_edit, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AccountEditViewModel.class);
        // TODO: Use the ViewModel
    }

}