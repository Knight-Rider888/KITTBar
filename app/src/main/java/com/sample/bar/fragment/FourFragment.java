package com.sample.bar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sample.bar.R;


public class FourFragment extends Fragment {

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e(getClass().getSimpleName(), "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(getClass().getSimpleName(), "onCreate");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(getClass().getSimpleName(), "onCreateView");
        return inflater.inflate(R.layout.fragment_four, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(getClass().getSimpleName(), "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(getClass().getSimpleName(), "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(getClass().getSimpleName(), "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(getClass().getSimpleName(), "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(getClass().getSimpleName(), "onStop");
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(getClass().getSimpleName(), "onDestroyView");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(getClass().getSimpleName(), "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(getClass().getSimpleName(), "onDetach");
    }
}