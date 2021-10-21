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

import org.w3c.dom.Text;

import java.net.URL;
import java.util.List;

public class RVBuyProductListAdapter extends RecyclerView.Adapter<RVBuyProductListAdapter.ProductViewHolder> {

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView nameProduct;
        TextView descriptionProduct;
        TextView priceProduct;
        ImageView imageProduct;
        EditText quantityProduct;
        TextView totalPriceProduct;

        public ProductViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            nameProduct = (TextView) itemView.findViewById(R.id.textViewProductName);
            descriptionProduct = (TextView) itemView.findViewById(R.id.textViewProductDescription);
            priceProduct = (TextView) itemView.findViewById(R.id.textViewProductPrice);
            imageProduct = (ImageView) itemView.findViewById(R.id.imageViewProductImage);
            quantityProduct = (EditText) itemView.findViewById(R.id.editTextQuantityProductToBuy);
            totalPriceProduct = (TextView) itemView.findViewById(R.id.textViewTotalPriceProduct);
        }

    }

    private List<Prodotto> listaProdotti;

    public RVBuyProductListAdapter(List<Prodotto> listaProdotti){
        this.listaProdotti = listaProdotti;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.buy_product_list_recycler_view_item, parent, false);
        ProductViewHolder pvh = new ProductViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.nameProduct.setText(listaProdotti.get(position).getNome());
        holder.descriptionProduct.setText(listaProdotti.get(position).getDescrizione());
        holder.priceProduct.setText(Float.toString(listaProdotti.get(position).getPrezzo()));

        String urlImage = listaProdotti.get(position).getImmagine();
        //TODO translate url to image
        //holder.imageProduct.setImageResource(listaProdotti.get(position).getImmagine());

        //holder.quantityProduct.setText(listaProdotti.get(position).getQuantity());
        //holder.quantityProduct.setText(listaProdotti.get(position).getTotalPrice());

    }

    @Override
    public int getItemCount() {
        return listaProdotti.size();
    }

}
