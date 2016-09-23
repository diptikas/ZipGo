package com.zipgo.diptika.zipgoassignment.ui.adapter;

import android.content.Context;


import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import com.zipgo.diptika.zipgoassignment.R;


import com.zipgo.diptika.zipgoassignment.ui.viewmodel.RouteModel;


import java.util.List;



/**
 * Created by diptika.s on 27/08/16.
 */
public class RoutesListAdapter extends RecyclerView.Adapter<RoutesListAdapter.ViewHolder> {
    private List<RouteModel> routeModelList;
    private Context mContext;

    public RoutesListAdapter(Context context, List<RouteModel> routeModelList) {
        mContext = context;
       this.routeModelList=routeModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routes_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RouteModel routeModel = routeModelList.get(position);
        holder.routeStops.setText("Stops : "+TextUtils.join(" , ", routeModel.getStops_sequence()));
        holder.routeDesc.setText("Description : " + routeModel.getDescription());
        holder.routeName.setText("Name : "+routeModel.getName());
        holder.routeID.setText("ID : "+routeModel.getId());
    }


    @Override
    public int getItemCount() {
        return routeModelList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView routeID;
        public TextView routeName;
        public TextView routeDesc;
        public TextView routeStops;
        public View view;

        /**
         * @param view
         */
        public ViewHolder(View view) {
            super(view);
            this.view = view;
            routeID = (TextView) view.findViewById(R.id.route_id);
            routeName = (TextView) view.findViewById(R.id.route_name);
            routeDesc = (TextView) view.findViewById(R.id.route_desc);
            routeStops = (TextView) view.findViewById(R.id.route_stops);
        }

        public void setTag(int id, int tag) {
            view.setTag(id, tag);
        }


    }
}
