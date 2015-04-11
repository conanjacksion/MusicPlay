package com.hvph.musicplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.hvph.musicplay.dao.DaoFactory;
import com.hvph.musicplay.dao.MPBaseDao;
import com.hvph.musicplay.model.Album;
import com.hvph.musicplay.model.Model;
import com.hvph.musicplay.model.Song;

import java.util.ArrayList;

/**
 * Created by bibo on 10/01/2015.
 */
public abstract class AbsMPBaseAdapter<T extends Model> extends BaseAdapter{
    public ArrayList<T> mItemList;
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected MPBaseDao<T> mMPBaseDao;
    protected int mDataType;

    protected AbsMPBaseAdapter(Context context, ArrayList<T> itemList, int dataType){
        this.mContext = context;
        this.mItemList = itemList;
        mInflater = LayoutInflater.from(this.mContext);
        this.mDataType = dataType;
        this.mMPBaseDao = DaoFactory.getDao(mDataType);
    }

    public ArrayList<T> getData() {
        return mItemList;
    }

    public void setData(ArrayList<T> itemList) {
        this.mItemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public T getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(mItemList.get(position).getId());
    }

    /**
     * remove item from list data in adapter
     * @param position
     */
    public void removeItem(int position){
        T item = getItem(position);
        mItemList.remove(position);
        notifyDataSetChanged();
    }

    /**
     * add item from list data in adapter
     * @param position
     * @param item
     */
    public void addItem(int position, T item){
        mItemList.add(position, item);
        notifyDataSetChanged();
    }

    /**
     * delete item from database
     * @param item
     */
    public void deleteItem(T item){
        mMPBaseDao.delete(item);
    }

    /**
     * insert item to database
     * @param item
     */
    public void insertItem(T item){
        mMPBaseDao.insert(item);
    }
}
