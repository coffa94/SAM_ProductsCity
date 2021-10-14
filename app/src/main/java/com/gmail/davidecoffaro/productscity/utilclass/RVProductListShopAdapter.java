package com.gmail.davidecoffaro.productscity.utilclass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.davidecoffaro.productscity.R;

import java.net.URL;
import java.util.List;

public class RVProductListShopAdapter extends RecyclerView.Adapter<RVProductListShopAdapter.ProductViewHolder> {

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView nameProduct;
        TextView priceProduct;
        ImageView imageProduct;

        public ProductViewHolder(View itemView){
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            nameProduct = (TextView) itemView.findViewById(R.id.textViewProductName);
            priceProduct = (TextView) itemView.findViewById(R.id.textViewProductPrice);
            imageProduct = (ImageView) itemView.findViewById(R.id.imageViewProductImage);
        }

    }

    private List<Prodotto> listaProdotti;

    public RVProductListShopAdapter(List<Prodotto> listaProdotti){
        this.listaProdotti = listaProdotti;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_recycler_view_item, parent, false);
        ProductViewHolder pvh = new ProductViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.nameProduct.setText(listaProdotti.get(position).getNome());
        holder.priceProduct.setText(Float.toString(listaProdotti.get(position).getPrezzo()));

        URL urlImage = listaProdotti.get(position).getImmagine();
        //TODO translate url to image
        //holder.imageProduct.setImageResource(listaProdotti.get(position).getImmagine());
    }

    @Override
    public int getItemCount() {
        return listaProdotti.size();
    }

}
