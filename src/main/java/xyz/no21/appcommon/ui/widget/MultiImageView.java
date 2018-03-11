package xyz.no21.appcommon.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import xyz.no21.appcommon.R;
import xyz.no21.appcommon.ui.CommonAdapter;

/**
 * Created by cookie on 2017/11/25.
 * Email:l437943145@gmail.com
 * Desc
 */

public class MultiImageView extends RecyclerView {


    private CommonAdapter<String> adapter;
    private List<String> images = new ArrayList<>();
    private int addImageIcon;

    private OnAddIconClickListener onAddIconClickListener;
    private int deleteImageIcon;

    public MultiImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MultiImageView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MultiImageView);
        addImageIcon = typedArray.getResourceId(R.styleable.MultiImageView_addImageIcon, -1);
        deleteImageIcon = typedArray.getResourceId(R.styleable.MultiImageView_deleteImageIcon, -1);
        typedArray.recycle();

        setLayoutManager(new GridLayoutManager(context, 3));
        adapter = new CommonAdapter<String>() {
            @Override
            public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new CommonViewHolder(inflaterView(parent, R.layout.app_item_multi_image));
            }

            @Override
            public void onBindViewHolder(final CommonViewHolder holder, int position) {
                holder.itemView.setOnClickListener(null);
                ImageView deleteView = holder.findViewById(R.id.delete, ImageView.class);

                if (addImageIcon == -1 || position < getItemCount() - 1) {
                    final String bean = images.get(position);

                    if (deleteImageIcon != -1) {
                        deleteView.setImageResource(deleteImageIcon);
                        deleteView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                images.remove(bean);
                                notifyItemRemoved(holder.getAdapterPosition());
                            }
                        });
                    }

                    holder.findViewById(R.id.cover, ImageFrescoView.class).setImageURI(bean);

                } else {
                    deleteView.setImageBitmap(null);
                    deleteView.setOnClickListener(null);

                    holder.findViewById(R.id.cover, ImageFrescoView.class).setActualImageResource(addImageIcon);
                    deleteView.setOnClickListener(null);
                    holder.itemView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onAddIconClickListener != null) {
                                onAddIconClickListener.onAddClick();
                            }
                        }
                    });
                }
            }

            @Override
            public int getItemCount() {
                return addImageIcon == -1 ? images.size() : images.size() + 1;
            }
        };
        setAdapter(adapter);
    }


    public void addImage(String imageBean) {
        images.add(imageBean);
        adapter.notifyDataSetChanged();
    }

    public void setOnAddIconClickListener(OnAddIconClickListener onAddIconClickListener) {
        this.onAddIconClickListener = onAddIconClickListener;
    }

    public List<String> getImages() {
        return images;
    }

    public interface OnAddIconClickListener {
        void onAddClick();
    }
}
