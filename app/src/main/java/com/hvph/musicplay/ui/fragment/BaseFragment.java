package com.hvph.musicplay.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.AbsListView;
import android.widget.ListView;
import com.hvph.musicplay.adapter.AbsMPBaseAdapter;
import com.hvph.musicplay.model.Model;
import com.hvph.swipetodismiss.SwipeDismissList;


/**
 * Created by bibo on 10/01/2015.
 */
public abstract class BaseFragment<T extends Model> extends Fragment{
    private SwipeDismissList mSwipeDismissList;
    private SwipeDismissList.OnDismissCallback mOnDismissCallback;

    /**
     * force fragment to load data again
     */
    public abstract void loadData();

    protected <T extends Model> void setDismissItem(final AbsMPBaseAdapter<T> adapter, ListView listView){
        mOnDismissCallback = new SwipeDismissList.OnDismissCallback() {
            @Override
            public SwipeDismissList.Undoable onDismiss(AbsListView listView, final int position) {
                final T itemDeleted = adapter.getItem(position);
                adapter.removeItem(position);
                return new SwipeDismissList.Undoable() {
                    @Override
                    public void undo() {
                        adapter.addItem(position,itemDeleted);
                    }

                    @Override
                    public String getTitle() {
                        return itemDeleted.getName();
                    }

                    @Override
                    public void discard() {
                        adapter.deleteItem(itemDeleted);
                    }
                };
            }
        };
        mSwipeDismissList = new SwipeDismissList(listView,mOnDismissCallback, SwipeDismissList.UndoMode.MULTI_UNDO);
        mSwipeDismissList.setAutoHideDelay(1000);
        listView.setOnTouchListener(mSwipeDismissList);
    }

    @Override
    public void onStop() {
        super.onStop();
        mSwipeDismissList.discardUndo();
    }
}
