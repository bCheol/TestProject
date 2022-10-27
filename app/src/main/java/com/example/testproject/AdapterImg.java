package com.example.testproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterImg extends RecyclerView.Adapter<AdapterImg.ViewHolder> implements ListenerImgAdd, ListenerImgDelete, ListenerItemTouchHelper {

    private final ArrayList<Uri> uriArrayList = new ArrayList<>();
    ListenerImgAdd listenerImgAdd;
    ListenerImgDelete listenerImgDelete;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.main_item_imgview, parent, false);
        return new ViewHolder(itemView, this,this);
    }

    @SuppressLint("CutPasteId")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //핸드폰 해상도 구하기
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) holder.itemView.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // 핸드폰의 가로 해상도를 구함.
        double deviceWidth = displayMetrics.widthPixels;
        // 핸드폰의 세로 해상도를 구함.
        // int deviceHeight = displayMetrics.heightPixels;
        // 아이템뷰 비율 지정 후 할당
        deviceWidth = deviceWidth / 4.5;
        int deviceHeight = (int) deviceWidth ;
        holder.itemView.getLayoutParams().width = (int) deviceWidth;
        holder.itemView.getLayoutParams().height = deviceHeight;
        // 아이템뷰의 이미지뷰 비율 할당
        holder.itemView.findViewById(R.id.imgView).getLayoutParams().width = (int) deviceWidth ;
        holder.itemView.findViewById(R.id.imgView).getLayoutParams().height = (int)(deviceWidth *0.9);
        // 비율 적용
        holder.itemView.requestLayout();

        if(position==0){
            holder.itemView.findViewById(R.id.imgDeleteBtn).setVisibility(View.INVISIBLE);
            holder.itemView.findViewById(R.id.imgView).setPadding(15,15,15,15);
            int listSize = uriArrayList.size();
            holder.setData(listSize);
        }else{
            holder.itemView.findViewById(R.id.imgNumView).setVisibility(View.INVISIBLE);
            Uri uri = uriArrayList.get(position);
            holder.setData(uri);
        }
/*
        holder.itemView.findViewById(R.id.imgNumView).setVisibility(View.INVISIBLE);
        Uri uri = uriArrayList.get(position);
        holder.setData(uri);
*/
    }

    @Override
    public int getItemCount() {
        return uriArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        Button deleteButton;
        TextView imgNumView;
        public ViewHolder(@NonNull View itemView, final ListenerImgAdd listenerImgAdd, final ListenerImgDelete listenerImgDelete) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listenerImgAdd != null){
                        listenerImgAdd.onImgAddListener(ViewHolder.this, view, position);
                    }
                }
            });
            deleteButton = itemView.findViewById(R.id.imgDeleteBtn);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listenerImgDelete != null){
                        listenerImgDelete.onImgDeleteListener(ViewHolder.this, view, position);
                    }
                }
            });
            imgNumView = itemView.findViewById(R.id.imgNumView);
        }

        @SuppressLint("SetTextI18n")
        public void setData(int listSize){
            int imgNum = listSize - 1;
            imgNumView.setText(imgNum +"/20");
            Glide.with(itemView.getContext())
                    .load(R.drawable.img_camera)
                    .fitCenter()
                    .into(imageView);
        }
        public void setData(Uri uri){
            Glide.with(itemView.getContext())
                    .load(uri)
                    .fitCenter()
                    .into(imageView);
            //    imageView.setImageURI(uri);
        }
    }
    //uri 추가
    public void addUri(Uri uri){
        uriArrayList.add(uri);
    }
    //uri 삭제
    public void deleteUri(int position){
        uriArrayList.remove(position);
    }

    public ArrayList backList() {
        return uriArrayList;
    }

    //이미지 추가 리스너
    public void setImgAddListener(ListenerImgAdd listenerImgAdd){
        this.listenerImgAdd = listenerImgAdd;
    }
    //이미지 추가 클릭
    @Override
    public void onImgAddListener(ViewHolder holder, View view, int position) {
        if(listenerImgAdd != null && position ==0){ //첫 아이템 이미지만 클릭 가능
            listenerImgAdd.onImgAddListener(holder, view, position);
        }
    }

    //이미지 삭제 리스너
    public void setImgDeleteListener(ListenerImgDelete listenerImgDelete){
        this.listenerImgDelete = listenerImgDelete;
    }
    //이미지 삭제 클릭
    @Override
    public void onImgDeleteListener(ViewHolder holder, View view, int position) {
        if(listenerImgDelete != null){
            listenerImgDelete.onImgDeleteListener(holder, view, position);
        }
    }
    //이미지 순서 바꾸기
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if(fromPosition !=0 && toPosition !=0) {
            Uri number = uriArrayList.get(fromPosition);
            uriArrayList.remove(fromPosition);
            uriArrayList.add(toPosition, number);
            notifyItemMoved(fromPosition, toPosition);
        }
        return true;
    }
}