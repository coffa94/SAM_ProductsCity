package com.gmail.davidecoffaro.productscity.utilclass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.davidecoffaro.productscity.R;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

public class RVCartProductListAdapter extends RecyclerView.Adapter<RVCartProductListAdapter.ProductViewHolder> {

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView nameProduct;
        TextView descriptionProduct;
        ImageView imageProduct;
        TextView quantityProduct;
        TextView totalPriceProduct;

        public ProductViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            nameProduct = (TextView) itemView.findViewById(R.id.textViewProductName);
            descriptionProduct = (TextView) itemView.findViewById(R.id.textViewProductDescriptionCart);
            imageProduct = (ImageView) itemView.findViewById(R.id.imageViewProductImage);
            quantityProduct = (TextView) itemView.findViewById(R.id.textViewQuantityProductToBuy);
            totalPriceProduct = (TextView) itemView.findViewById(R.id.textViewTotalPriceProduct);
        }

    }

    private List<Prodotto> listaProdotti;

    public RVCartProductListAdapter(List<Prodotto> listaProdotti){
        this.listaProdotti = listaProdotti;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product_list_recycler_view_item, parent, false);
        ProductViewHolder pvh = new ProductViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.nameProduct.setText(listaProdotti.get(position).getNome());
        holder.descriptionProduct.setText(listaProdotti.get(position).getDescrizione());
        holder.quantityProduct.setText(String.valueOf(listaProdotti.get(position).getQuantita()));
        holder.totalPriceProduct.setText(String.valueOf((float) listaProdotti.get(position).getQuantita()* listaProdotti.get(position).getPrezzo()));

        String urlImage = listaProdotti.get(position).getUrlimmagine();
        //translate url to image

        Picasso.get()
                .load(urlImage) // URL or file
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imageProduct); // An ImageView object to show the loaded image

    }

    @Override
    public int getItemCount() {
        return listaProdotti.size();
    }

}
