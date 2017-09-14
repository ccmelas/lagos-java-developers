package com.melas.javadevelopers_lagos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.melas.javadevelopers_lagos.models.Developer;
import com.melas.javadevelopers_lagos.utilities.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by melas on 9/14/17.
 */

public class DeveloperAdapter extends RecyclerView.Adapter<DeveloperAdapter.DeveloperViewHolder>{

    final private ListItemClickListener listener;
    ArrayList<Developer> developersList;
    private Context context;

    public interface ListItemClickListener {
        void onListItemClick(int itemClickedIndex);
    }

    public DeveloperAdapter(ArrayList<Developer> developersList, ListItemClickListener listener) {
        this.developersList = developersList;
        this.listener = listener;
    }

    @Override
    public DeveloperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.developer, parent, false);
        return new DeveloperViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DeveloperViewHolder holder, int position) {
        Developer developer = developersList.get(position);
        holder.usernameView.setText(developer.getUsername());
        Picasso.with(context).load(developer.getAvatar()).transform(new CircleTransform()).into(holder.avatarView);
    }

    @Override
    public int getItemCount() {
        return developersList.size();
    }

    class DeveloperViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView avatarView;
        TextView usernameView;

        public DeveloperViewHolder(View itemView) {
            super(itemView);
            avatarView = itemView.findViewById(R.id.avatar);
            usernameView = itemView.findViewById(R.id.username);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            listener.onListItemClick(position);
        }
    }
}
