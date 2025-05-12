package com.duyer;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.duyer.filmleruygulamasi.FilmlerActivity;
import com.duyer.filmleruygulamasi.R;

import java.util.ArrayList;

public class KategorilerAdapter extends RecyclerView.Adapter<KategorilerAdapter.CardTasarimTutucu> {
    private Context mContext;
    private ArrayList<Kategoriler> kategoriListe;

    public KategorilerAdapter(Context mContext, ArrayList<Kategoriler> kategoriListe) {
        this.mContext = mContext;
        this.kategoriListe = kategoriListe;
    }

    @NonNull
    @Override
    public CardTasarimTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kategori_card_tasarim,parent,false);
        return new CardTasarimTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardTasarimTutucu holder, int position) {
        Kategoriler kategori = kategoriListe.get(position);
        holder.textViewKategoriAd.setText(kategori.getKategori_ad());

        holder.kategori_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FilmlerActivity.class);
                intent.putExtra("kategori_id", kategori.getKategori_id());
                intent.putExtra("kategori_ad", kategori.getKategori_ad());
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return kategoriListe.size();
    }

    public class CardTasarimTutucu extends RecyclerView.ViewHolder{
        private CardView kategori_card;
        private TextView textViewKategoriAd;

        public CardTasarimTutucu(@NonNull View itemView) {
            super(itemView);
            kategori_card = itemView.findViewById(R.id.kategori_card);
            textViewKategoriAd = itemView.findViewById(R.id.textViewKategoriAd);
        }
    }
}

