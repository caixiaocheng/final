package com.gyzj.core.ui.activity;

import java.io.File;
import java.util.ArrayList;

import com.example.core.R;
import com.gyzj.core.util.CameraUtil;
import com.gyzj.core.util.ToastUtil;
import com.gyzj.core.util.cache.FileCache;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


/**
 * 图片选择
 * 
 * @author  陈晓磊/Michael Chen
 * @date  2014年4月23日
 */
public class SettingAuthorSelectPicActivty extends Activity implements
        OnClickListener
{
    /**
     * 拍照
     */
    private final int REPCODE_PIC = 1;
    
    /**
     * 选择本地图片
     */
    private final int REPCODE_SELECT_PIC = 2;
    
    private Button btPic;
    
    private Button btSelectPic;
    
    private Button btCancel;
    
    private String fileName;
    
    private int type;
    private boolean isThumb;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_select_pic_activity);
        
        btPic = (Button) findViewById(R.id.bt_setting_pic);
        btPic.setOnClickListener(this);
        
        btSelectPic = (Button) findViewById(R.id.bt_setting_selectpic);
        btSelectPic.setOnClickListener(this);
        
        btCancel = (Button) findViewById(R.id.bt_setting_cancel);
        btCancel.setOnClickListener(this);
        
        type = getIntent().getIntExtra("type", 0);
        isThumb = getIntent().getBooleanExtra("isThumb", true);
    }
    
    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        switch (id)
        {
            case R.id.bt_setting_pic:
            {
                
                boolean isCameraUse = CameraUtil.checkCheckCamera(this);
                if (isCameraUse == false)
                {
                    ToastUtil.toastshort(this, "无法使用摄像头");
                    return;
                }
                

                
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileName = FileCache.getInstance().getImgFileName();
                File file = new File(fileName);
                Uri uri = Uri.fromFile(file);
                it.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(it, REPCODE_PIC);
                
               /* Uri imageUri = null;
				String fileName = null;
				Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				
					
					fileName = "image.jpg";
				
				imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),fileName));
				//指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
				openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(openCameraIntent, 1);*/
                break;
            }
            case R.id.bt_setting_selectpic:
            {
             // Intent it = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                 Intent it = new Intent(this,ImgFileListActivity.class);
                it.putExtra("isThumb", isThumb);
                startActivityForResult(it, REPCODE_SELECT_PIC);
                break;
            }
            case R.id.bt_setting_cancel:
            {
                onBackPressed();
                
                break;
            }
            default:
                break;
        }
    }
    
    @Override
    public void onBackPressed()
    {
        this.finish();
       // overridePendingTransition(0, R.anim.push_down_out);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (REPCODE_PIC == requestCode)
        {   ArrayList list = new ArrayList();
            list.add(fileName);
            Intent it = new Intent();
            it.putExtra("files", list);
            setResult(resultCode, it);
            finish();
        }
        else if (requestCode == REPCODE_SELECT_PIC && null != data)
        {
            /*Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn,
                    null,
                    null,
                    null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();*/
            ArrayList list = data.getStringArrayListExtra("files");
            Intent it = new Intent();
            it.putExtra("files", list);
            it.putExtra("type", type);
            setResult(resultCode, it);
            finish();
        }
        
    }
}
