package mes.cheveux.salon.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mes.cheveux.salon.R;

public class HomeSalonModulesAdapter extends RecyclerView.Adapter<HomeSalonModulesAdapter.ModuleViewHolder> {
    private Context moduleContext;
    private HomeSalonAdapter salonAdapter;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private List<ModuleModel> moduleList;


    public HomeSalonModulesAdapter(Context screenContext,
                                   List<ModuleModel> screenList) {
        this.moduleContext = screenContext;
        this.moduleList = screenList;
        this.recycledViewPool = new RecyclerView.RecycledViewPool();

    }

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View theView = LayoutInflater
                .from(moduleContext)
                .inflate(R.layout.home_module_item, parent, false);

        return new ModuleViewHolder(theView);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        holder.txtViewModuleName.setText(moduleList.get(position).getName());
        salonAdapter = new HomeSalonAdapter(moduleContext, moduleList.get(position).getSalons());

        holder.moduleRecyclerView.setAdapter(salonAdapter);
        holder.moduleRecyclerView.setRecycledViewPool(recycledViewPool);

    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    class ModuleViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView moduleRecyclerView;
        private TextView txtViewModuleName;

        private LinearLayoutManager horizontalManager = new LinearLayoutManager(moduleContext,
                LinearLayoutManager.HORIZONTAL, false);


        public ModuleViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewModuleName = itemView.findViewById(R.id.txtViewModuleName);
            moduleRecyclerView = itemView.findViewById(R.id.moduleRecyclerView);
            moduleRecyclerView.setLayoutManager(horizontalManager);
            moduleRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }
    }
}
