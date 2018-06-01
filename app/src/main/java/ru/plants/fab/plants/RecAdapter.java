package ru.plants.fab.plants;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.plants.fab.plants.model.Plant;
//этот класс является адаптером для списка растений
//тут заполняется список, еще тут есть класс. который определяет все элементы внутри оной записи списка
public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder>{
    List<Plant> data;

    public RecAdapter(List<Plant> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    //тут каждый элемент заполняется данными 
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Plant plant = data.get(position);
        holder.name.setText(plant.getName());
        holder.place.setText(plant.getPlace());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //этот класс определяет все элементы, которые есть в одном эелементе
    //кароче название, место и три картинки устанавливаются тут
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView place;
        ImageView wet;
        ImageView thermometer;
        ImageView cloud;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textName);
            place = itemView.findViewById(R.id.textPlace);
            wet = itemView.findViewById(R.id.imageWet);
            thermometer = itemView.findViewById(R.id.imageTherm);
            cloud = itemView.findViewById(R.id.imageCloud);
        }
    }
}
