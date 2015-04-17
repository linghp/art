package com.shangxian.art.view;

import com.shangxian.art.R;

import android.app.Service;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**  
 * 一个没有滚动条的LinearLayout，但是却很像GridView。  
 * 实际上，就是为了解决当GridView被包含在一个SrcollView中而却拥有两个滚动条相互排斥而做。  
 */  
public class GridLinearLayout extends LinearLayout{   
    /**仿GridView单元格适配器*/  
    private BaseAdapter adapter;   
    /**仿GridView列数*/  
    private int columns=1;   
    /**仿GridView行数*/  
    private int rows=1;   
    /**仿GridView填充的单元格总数*/  
    private int count;   
    /**仿GridView单元格单击事件*/  
    private OnCellClickListener onCellClickListener;   
    /**布局水平分割线宽度*/  
    private int horizontalSpace =1;   
    /**布局垂直分割线宽度*/  
    private int verticalSpace =1;   
    /**  
     * 仿GridView构造方法  
     * @param context  
     */  
    public GridLinearLayout(Context context) {   
        super(context);   
    }   
 
    /**  
     * 仿GridV构造方法  
     * @param context  
     * @param attrs  
     */  
    public GridLinearLayout(Context context, AttributeSet attrs) {   
        super(context, attrs);   
        initAttrs(context, attrs);   
    }   
  
    /***  
     * 加载xml文件设置的属性值  
     * @param context  
     * @param attrs  
     */  
    private void initAttrs(Context context ,AttributeSet attrs){   
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GridLinearLayout);   
        columns = typedArray.getInteger(R.styleable.GridLinearLayout_columns, 1);   
        rows = typedArray.getInteger(R.styleable.GridLinearLayout_rows, 1);   
        horizontalSpace = typedArray.getDimensionPixelSize(R.styleable.GridLinearLayout_horizontalSpace, 0);   
        verticalSpace = typedArray.getDimensionPixelSize(R.styleable.GridLinearLayout_verticalSpace, 0);   
        typedArray.recycle();   
    }   
       
    /**  
     * 构造水平显示LinearLayout用于填充cell  
     * @return  
     */  
    private LinearLayout rowLinearLayout(){   
        LinearLayout itemLinearLayout = new LinearLayout(getContext());   
        itemLinearLayout.setOrientation(LinearLayout.HORIZONTAL);   
        itemLinearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));   
        itemLinearLayout.setGravity(Gravity.LEFT);   
        return itemLinearLayout;   
    }   
       
       
    /**  
     * 仿GridView将适配器单元格绑定到当前LinearLayout中  
     * @return  
     */  
    public void bindLinearLayout() {   
        removeAllViews();   
        rows = getSpecialRows(true);   
        if(rows==-1){   
            return;   
        }   
        for(int r=0;r<rows;r++){   
            LinearLayout itemRow = rowLinearLayout();   
            for(int c=0;c<columns;c++){   
                final int index = r*columns+c;   
                View cellView = adapter.getView(index, null, null);   
                cellView.setLayoutParams(cellLayoutParams(index));   
                if(index<count||index==(count-1)){   
                    itemRow.addView(cellView,c);   
                    cellView.setOnClickListener(new OnClickListener() {   
                        @Override  
                        public void onClick(View v) {   
                            if(onCellClickListener!=null){   
                                onCellClickListener.onCellClick(index);   
                            }   
                        }   
                    });   
                      
                    if(index==(count-1)){   
                        addView(itemRow, r);   
                        return;   
                    }   
                }else{   
                    return;   
                }   
            }   
            addView(itemRow, r);   
        }   
    }   
       
    /**  
    * 获取屏幕宽度用于均分一行LinearLayout各个Cell  
     * @return  
     */  
    @SuppressWarnings("deprecation")   
    private int screenWidth(){   
        /*Point outSize = new Point();  
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Service.WINDOW_SERVICE);  
        Display display = windowManager.getDefaultDisplay();  
        display.getSize(outSize);  
        return outSize.x;*/  
           
        int screenWidth;   
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Service.WINDOW_SERVICE);   
        Display display = windowManager.getDefaultDisplay();   
        screenWidth = display.getWidth();   
        return screenWidth;   
    }   
       
    /**  
   * 设置当前单元格边距，  
     * 每一行第一个左边距为0底边距为horizontalSpace，  
    * 其他单元格左边距为verticalSpace底边距为horizontalSpace  
     * @param index 单元格序数  
     * @return  
     */  
    private LayoutParams cellLayoutParams(int index){   
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(cellWidth(),LayoutParams.WRAP_CONTENT);   
        if(index%columns==0){   
            params.setMargins(0, 0, 0, horizontalSpace);   
            return params;   
        }else{   
            params.setMargins(verticalSpace, 0, 0, horizontalSpace);   
            return params;   
        }   
    }   
       
    /***  
     * 计算单元格宽度  
     * @return  
     */  
   private int cellWidth(){   
        int cellScreenWidth = screenWidth()-(columns-1)*verticalSpace;   
        return cellScreenWidth/columns;   
    }   
       
    /***  
     * 是否按列的倍数返回行数  
     * @param isSpecial TRUE:返回列的倍数行数，FALSE:有多少行显示多少行  
     * @return 返回-1时表示列数为0  
     */  
    private int getSpecialRows(boolean isSpecial){   
        try {   
            if(isSpecial){   
                return count/columns;   
            }else{   
                return (int) Math.ceil((double)count/columns);   
            }   
        } catch (Exception e) {   
            e.printStackTrace();   
            return -1;   
        }   
    }   
       
    /***  
     * 返回该控件的适配器  
     * @return  
     */  
    public BaseAdapter getAdapter() {   
        return adapter;   
    }   
  
   /**  
     * 设置该控件的适配器  
     * @param adapter 自定义适配器  
     */  
    public void setAdapter(BaseAdapter adapter) {   
        this.adapter = adapter;   
        this.count = adapter.getCount();   
    }   
  
    /**  
     * 返回该控件的列数  
     * @return  
     */  
    public int getColumns() {   
        return columns;   
    }   
  
    /**  
     * 设置该控件的列数  
     * @param columns 列数  
     */  
    public void setColumns(int columns) {   
        this.columns = columns;   
    }   
  
    /**  
    * 返回该控件的行数  
    * @return  
     */  
    public int getRows() {   
        return rows;   
    }   
  
   /**  
     * 设置该控件的行数  
     * @param rows 行数  
     */  
    public void setRows(int rows) {   
        this.rows = rows;   
    }   
  
    /**  
     * 返回该控件各单元格水平间距  
     * @return  
     */  
   public int getHorizontalSpace() {   
        return horizontalSpace;   
    }   

    /**  
    * 设置该控件各单元格水平间距  
     * @param horizontalSpace 间距  
     */  
    public void setHorizontalSpace(int horizontalSpace) {   
        this.horizontalSpace = horizontalSpace;   
    }   
  
    /**  
     * 返回该控件各单元格垂直间距  
     * @return  
     */  
    public int getVerticalSpace() {   
        return verticalSpace;   
    }   
  
    /**  
     * 设置该控件各单元格垂直间距  
     * @param verticalSpace 间距  
     */  
    public void setVerticalSpace(int verticalSpace) {   
        this.verticalSpace = verticalSpace;   
    }   
  
    public interface OnCellClickListener{   
        public void onCellClick(int index);   
    }   
       
    /**  
     * 监听响应单元格单击事件  
     * @param onCellClickListener 单元格单击事件接口  
   */  
    public void setOnCellClickListener(OnCellClickListener onCellClickListener){   
        this.onCellClickListener = onCellClickListener;   
    }   
  
}