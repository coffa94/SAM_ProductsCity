package com.gmail.davidecoffaro.productscity.utilclass;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.davidecoffaro.productscity.InfoShopActivity;
import com.gmail.davidecoffaro.productscity.NewProductActivity;
import com.gmail.davidecoffaro.productscity.R;

import java.util.List;

public class RVProductListShopAdapter extends RecyclerView.Adapter<RVProductListShopAdapter.ProductViewHolder> {

    public static class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cv;
        TextView nameProduct;
        TextView descriptionProduct;
        TextView priceProduct;
        ImageView imageProduct;
        String stringImage;

        public ProductViewHolder(View itemView){
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            nameProduct = (TextView) itemView.findViewById(R.id.textViewProductName);
            descriptionProduct = (TextView) itemView.findViewById(R.id.textViewProductDescription);
            priceProduct = (TextView) itemView.findViewById(R.id.textViewProductPrice);
            imageProduct = (ImageView) itemView.findViewById(R.id.imageViewProductImage);

            //set listener on an item in the recycler view
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(v.getContext(), NewProductActivity.class);
            i.putExtra("NameProduct" , nameProduct.getText());
            i.putExtra("DescriptionProduct" , descriptionProduct.getText());
            i.putExtra("PriceProduct", priceProduct.getText());
            i.putExtra("StringImageProduct", stringImage);
            i.putExtra("NumberListProduct", getAdapterPosition());
            //v.getContext().startActivity(i);
            ((InfoShopActivity) v.getContext()).startNewProductActivityForResult(i);

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
        holder.descriptionProduct.setText(listaProdotti.get(position).getDescrizione());
        holder.priceProduct.setText(Float.toString(listaProdotti.get(position).getPrezzo()));

        holder.stringImage = listaProdotti.get(position).getImmagine();
        //TODO translate url to image
        //ImageDownloaderTask imageDownloaderTask = new ImageDownloaderTask(holder.imageProduct);
        //imageDownloaderTask.execute(stringImage);
        //holder.imageProduct = null;
    }

    @Override
    public int getItemCount() {
        return listaProdotti.size();
    }

}
