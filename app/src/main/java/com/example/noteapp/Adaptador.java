package com.example.noteapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.Viewholder>
implements View.OnClickListener{



    private ArrayList<Modelo> modeloList;
    private View.OnClickListener listener;

    //PASO 3 crear el listener





    ///PASO 5 agregar como parametro recyclerviewlistener a este metodo llamado adaptador

    public Adaptador(ArrayList<Modelo> modeloList){

        this.modeloList = modeloList;

    }

    public void setOnClickListener (View.OnClickListener listener){

        this.listener = listener;




    }

    public void SentOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {

        if(listener!=null){
            listener.onClick(view);
        }
    }

    //PASO 4
    public static class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView txttitulo;
        private TextView txtDescripcion;
        private TextView  txtNota;
        private TextView txtFechaHora;

        public MyViewHolder(final View view ){

            super(view);


            ////Aqui creo que van las variables que estan los datos de la nota para que se invoquen en el nuevo activity, creo que los debe tomar del layout agregarnota

            ///pero no estoy seguro, lo marco asi entre comentarios

            //////////////////////////////////////////////////
            txttitulo = view.findViewById(R.id.textTitle);
            txtDescripcion = view.findViewById(R.id.txtDescripcion);
            txtNota = view.findViewById(R.id.txtNota);
            txtFechaHora = view.findViewById(R.id.fech);

            //////////////////////////////////////////////////////


        }


    }



    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, viewGroup, false);
        view.setOnClickListener(this);

        return new Viewholder(view);



    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int position) {

        int resource = modeloList.get(position).getImagenIcono();
        String titulo = modeloList.get(position).getTitulo();
        String cuerpo = modeloList.get(position).getCuerpo();
        viewholder.setData(resource,titulo,cuerpo);
        viewholder.setData(resource,titulo,cuerpo);
    }

    @Override
    public int getItemCount() {
        return modeloList.size();
    }






    //PASO 2

    public interface RecyclerViewClickListener {

        void OnClick ( View v , int position);

    }














    class Viewholder extends RecyclerView.ViewHolder {


        private ImageView imageView;
        private TextView titulo;
        private TextView cuerpo;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewnota);
            titulo = itemView.findViewById(R.id.textTitle);
            cuerpo = itemView.findViewById(R.id.txtDescripcion);

        }

        private void setData(int imageResource, String titulotexto, String cuerpoTexto){

            imageView.setImageResource(imageResource);
            titulo.setText(titulotexto);
            cuerpo.setText(cuerpoTexto);
        }
    }
}


