package mes.cheveux.salon.common.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public abstract class CommonPagedListAdapter<T, VH extends RecyclerView.ViewHolder>
        extends PagedListAdapter<T, VH> {

    private Context mContext;
    private ArrayList<T> mArrayList;

    protected CommonPagedListAdapter(Context context, ArrayList<T> arrayList,
                                     @NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
        this.mContext  = context;
        this.mArrayList = arrayList;
    }
}
