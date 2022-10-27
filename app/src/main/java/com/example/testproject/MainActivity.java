package com.example.testproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //날짜 텍스트뷰
    TextView dateView;
    String nowYear, nowMonth, nowDay;
    //사진첨부
    RecyclerView imgRecyclerView;
    AdapterImg adapterImg;
    ItemTouchHelper itemTouchHelper;
    //파일첨부
    TextView filePathView1, filePathView2;
    Button fileDeleteBtn1, fileDeleteBtn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //타이틀 X 버튼
        findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //타이틀 저장 버튼
        findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //파일변환

                //정보전달

                //서비스 실행

            }
        });

        //수신정보 - 아동
        findViewById(R.id.childBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         //       Intent intent = new Intent(getApplicationContext(), ActivityChild.class);
         //       getChild.launch(intent);
            }
        });

        //수신정보 - 날짜
        dateView = findViewById(R.id.dateView);
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat year = new SimpleDateFormat("yyyy");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat month = new SimpleDateFormat("MM");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat day = new SimpleDateFormat("dd");
        nowYear = year.format(date);
        nowMonth = month.format(date);
        nowDay = day.format(date);
        String nowDate1 = nowYear + "-" + nowMonth + "-" + nowDay;
        dateView.setText(nowDate1);

        //수신정보 - 날짜 선택
    /*    DialogFragment newFragment = new DatePickerFragment();
        findViewById(R.id.datePickBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });*/

        //수신정보 - 관찰일지로 연동
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchButton = findViewById(R.id.switchButton);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){  //체크된 경우

                }else{  //체크되지 않은 경우

                }
            }
        });

        //알림장 - 이전 내용 불러오기
   /*     findViewById(R.id.noticeBeforeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityNoticeBefore.class);
                getNoticeBefore.launch(intent);
            }
        });*/

        //알림장 - 작성 글
        EditText noticeEditText = findViewById(R.id.noticeEditText);
        String noticeText = noticeEditText.getText().toString();

        //알림장 - 아동생활
     /*   findViewById(R.id.childLifeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityChildLife.class);
                getChildLife.launch(intent);
            }
        });*/

        //첨부된 사진 리사이클러뷰
        imgRecyclerView = findViewById(R.id.imgRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        imgRecyclerView.setLayoutManager(gridLayoutManager);
        adapterImg = new AdapterImg();

        //바꿔야할 것
        Uri uri = Uri.parse("drawable/img_camera.png");
        adapterImg.addUri(uri);
        imgRecyclerView.setAdapter(adapterImg);

        //아이템 이동
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(adapterImg));

        //사진 첨부 버튼
        adapterImg.setImgAddListener(new ListenerImgAdd() {
            @Override
            public void onImgAddListener(AdapterImg.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/* video/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                getImg.launch(intent);
            }
        });

        //첨부된 이미지 삭제 버튼 클릭
        adapterImg.setImgDeleteListener(new ListenerImgDelete() {
            @Override
            public void onImgDeleteListener(AdapterImg.ViewHolder holder, View view, int position) {
                adapterImg.deleteUri(position);
                adapterImg.notifyItemRemoved(position);
            }
        });

        //파일1 첨부, 삭제
        filePathView1 = findViewById(R.id.filePathView1);
        fileDeleteBtn1 = findViewById(R.id.fileDeleteBtn1);
        fileDeleteBtn1.setVisibility(View.INVISIBLE);
        findViewById(R.id.fileAddBtn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //파일 인텐트
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                //카메라 인텐트
                Intent intent2 = new Intent();
                intent2.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

                Intent chooser = Intent.createChooser(intent, "첨부에 사용할 앱을 선택하세요.");
                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { intent2 });
                getFile1.launch(chooser);
            }
        });
        fileDeleteBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filePathView1.setText("파일경로");
                fileDeleteBtn1.setVisibility(View.INVISIBLE);
            }
        });

        //파일2 첨부, 삭제
        filePathView2 = findViewById(R.id.filePathView2);
        fileDeleteBtn2 = findViewById(R.id.fileDeleteBtn2);
        fileDeleteBtn2.setVisibility(View.INVISIBLE);
        findViewById(R.id.fileAddBtn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //파일 인텐트
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                //카메라 인텐트
                Intent intent2 = new Intent();
                intent2.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

                Intent chooser = Intent.createChooser(intent, "첨부에 사용할 앱을 선택하세요.");
                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { intent2 });

                getFile2.launch(chooser);
            }
        });
        fileDeleteBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filePathView2.setText("파일경로");
                fileDeleteBtn2.setVisibility(View.INVISIBLE);
            }
        });

        //상단, 하단 보여주기
        FloatingActionButton floatingBtn = findViewById(R.id.floatingBtn);
        NestedScrollView nestedScrollView = findViewById(R.id.nestedScrollView);
        //스크롤 시 플로팅 버튼 표시
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(!v.canScrollVertically(-1)) { //상단일 때
                    floatingBtn.setVisibility(View.VISIBLE);
                    floatingBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            nestedScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                }else if(!v.canScrollVertically(1)){ //하단일 때
                    floatingBtn.setVisibility(View.VISIBLE);
                    floatingBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            nestedScrollView.fullScroll(ScrollView.FOCUS_UP);
                        }
                    });
                }
                else {  //그 외
                    floatingBtn.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    //onNewIntent
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.getStringExtra("key").equals("2")){
            //설정한 날짜 받기
            nowYear = intent.getStringExtra("year") ;
            nowMonth = intent.getStringExtra("month") ;
            nowDay = intent.getStringExtra("day") ;
            String nowDate2 =  nowYear + "-" + nowMonth + "-" + nowDay ;
            dateView.setText(nowDate2);
        }
    }

    //아동 선택
    ActivityResultLauncher<Intent> getChild = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        if (result.getData() != null) {

                        }
                    }
                }
            });

    //알림장 이전 내용 불러오기
    ActivityResultLauncher<Intent> getNoticeBefore = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        if (result.getData() != null) {

                        }
                    }
                }
            });

    //아동생활
    ActivityResultLauncher<Intent> getChildLife = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        if (result.getData() != null) {

                        }
                    }
                }
            });

    //사진 가져오기
    ActivityResultLauncher<Intent> getImg = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        if (result.getData() != null) {
                            if (result.getData().getClipData().getItemCount() > 20) {
                                Toast.makeText(getApplicationContext(),"21개 이상 등록할 수 없습니다.",Toast.LENGTH_SHORT).show();

                            } else {
                                for (int i = 0; i < result.getData().getClipData().getItemCount(); i++) {
                                    adapterImg.addUri(result.getData().getClipData().getItemAt(i).getUri());
                                }
                            }
                            imgRecyclerView.setAdapter(adapterImg);
                            itemTouchHelper.attachToRecyclerView(imgRecyclerView);
                        }
                    }
                }
            });

    //파일가져오기1
    ActivityResultLauncher<Intent> getFile1 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        if (result.getData() != null) {
                            if(result.getData().getData()!=null){
                                filePathView1.setText(result.getData().getData().getPath());
                            }
                            else{
                                Bundle bundle = result.getData().getExtras();
                                Bitmap bitmap = (Bitmap) bundle.get("data");
                                filePathView1.setText(bitmap.toString());
                            }
                            fileDeleteBtn1.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

    //파일가져오기2
    ActivityResultLauncher<Intent> getFile2 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        if (result.getData() != null) {
                            if(result.getData().getData()!=null){
                                filePathView2.setText(result.getData().getData().toString());
                            }
                            else{
                                Bundle bundle = result.getData().getExtras();
                                Bitmap bitmap = (Bitmap) bundle.get("data");
                                filePathView2.setText(bitmap.toString());
                            }
                            fileDeleteBtn2.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
}