package com.gyzj.core.widget;

import com.example.core.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;


public class SearcheditText extends EditText implements 
        TextWatcher
{
    /**
     * ɾ����ť������
     */
    private Drawable mClearDrawable;
    
    /**
     * �ؼ��Ƿ��н���
     */
    private boolean hasFoucs;
    
    private OnTextChangeListener onTextChangeListener;
    
    public void setOnTextChangeListener(
            OnTextChangeListener onTextChangeListener)
    {
        this.onTextChangeListener = onTextChangeListener;
    }
    
    private OnSearchClickListener onDeleteClickListener;
    
    public void setOnDeleteClickListener(
    		OnSearchClickListener onDeleteClickListener)
    {
        this.onDeleteClickListener = onDeleteClickListener;
    }
    
    public SearcheditText(Context context)
    {
        this(context, null);
    }
    
    public SearcheditText(Context context, AttributeSet attrs)
    {
        //���ﹹ�췽��Ҳ����Ҫ����������ܶ����Բ�����XML���涨��
        this(context, attrs, android.R.attr.editTextStyle);
    }
    
    public SearcheditText(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }
    
    private void init()
    {
        //��ȡEditText��DrawableRight,����û���������Ǿ�ʹ��Ĭ�ϵ�ͼƬ
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null)
        {
            //        	throw new NullPointerException("You can add drawableRight attribute in XML");
            mClearDrawable = getResources().getDrawable(R.drawable.icon_search_hover);
        }
        
        mClearDrawable.setBounds(0,
                0,
                mClearDrawable.getIntrinsicWidth(),
                mClearDrawable.getIntrinsicHeight());
        //Ĭ����������ͼ��
        setClearIconVisible(false);
        //���ý���ı�ļ���
      //  setOnFocusChangeListener(this);
        //����������������ݷ����ı�ļ���
        Drawable right = mClearDrawable;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1],
                right,
                getCompoundDrawables()[3]);
        addTextChangedListener(this);
    }
    
    /**
     * ��Ϊ���ǲ���ֱ�Ӹ�EditText���õ���¼������������ü�ס���ǰ��µ�λ����ģ�����¼�
     * �����ǰ��µ�λ�� ��  EditText�Ŀ��� - ͼ�굽�ؼ��ұߵļ�� - ͼ��Ŀ���  ��
     * EditText�Ŀ��� - ͼ�굽�ؼ��ұߵļ��֮�����Ǿ�������ͼ�꣬��ֱ�����û�п���
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_UP)
        {
            if (getCompoundDrawables()[2] != null)
            {
                
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                
                if (touchable)
                {
                   
                    if (onDeleteClickListener != null)
                    {
                        onDeleteClickListener.onDeleteClick();
                    }
                }
            }
        }
        
        return super.onTouchEvent(event);
    }
    
    /**
     * ��ClearEditText���㷢���仯��ʱ���ж������ַ��������������ͼ�����ʾ������
     */
   /* @Override
    public void onFocusChange(View v, boolean hasFocus)
    {
        this.hasFoucs = hasFocus;
        if (hasFocus)
        {
            setClearIconVisible(getText().length() > 0);
        }
        else
        {
            setClearIconVisible(false);
        }
    }*/
    
    /**
     * �������ͼ�����ʾ�����أ�����setCompoundDrawablesΪEditText������ȥ
     * @param visible
     */
    protected void setClearIconVisible(boolean visible)
    {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1],
                right,
                getCompoundDrawables()[3]);
    }
    
    /**
     * ��������������ݷ����仯��ʱ��ص��ķ���
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after)
    {
        if (hasFoucs)
        {
            setClearIconVisible(s.length() > 0);
        }
        
        if (onTextChangeListener != null)
        {
            onTextChangeListener.onTextChanged(s, start, count, after);
        }
    }
    
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
            int after)
    {
        if (onTextChangeListener != null)
        {
            onTextChangeListener.beforeChange(s, start, count, after);
        }
    }
    
    @Override
    public void afterTextChanged(Editable s)
    {
        if (onTextChangeListener != null)
        {
            onTextChangeListener.afterChange(s);
        }
    }
    
    /**
     * ���ûζ�����
     */
    public void setShakeAnimation()
    {
        this.setAnimation(shakeAnimation(5));
    }
    
    /**
     * �ζ�����
     * @param counts 1���ӻζ�������
     * @return
     */
    public static Animation shakeAnimation(int counts)
    {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }
    
   
    public interface OnSearchClickListener
    {
        public void onDeleteClick();
    }
    
    /**
     * �ı��仯����
     * 
     * @author �̲�
     * @date  2014��10��10��
     */
    public interface OnTextChangeListener
    {
        public void beforeChange(CharSequence s, int start, int count, int after);
        
        public void afterChange(Editable s);
        
        public void onTextChanged(CharSequence s, int start, int count,
                int after);
    }
}