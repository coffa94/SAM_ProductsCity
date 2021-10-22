package com.gmail.davidecoffaro.productscity.utilclass;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
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

    public static class ProductViewHolder extends RecyclerView.ViewHolder implements TextWatcher {
        CardView cv;
        TextView nameProduct;
        TextView descriptionProduct;
        TextView priceProduct;
        ImageView imageProduct;
        EditText quantityProduct;
        TextView totalPriceProduct;

        //total order view management
        TextView totalOrder;

        public ProductViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            nameProduct = (TextView) itemView.findViewById(R.id.textViewProductName);
            descriptionProduct = (TextView) itemView.findViewById(R.id.textViewProductDescription);
            priceProduct = (TextView) itemView.findViewById(R.id.textViewProductPrice);
            imageProduct = (ImageView) itemView.findViewById(R.id.imageViewProductImage);
            quantityProduct = (EditText) itemView.findViewById(R.id.editTextQuantityProductToBuy);
            totalPriceProduct = (TextView) itemView.findViewById(R.id.textViewTotalPriceProduct);

            //add listener on text changed
            quantityProduct.addTextChangedListener(this);

            //get view total order from BuyProductsActivity
            totalOrder = (TextView) ((Activity)itemView.getContext()).findViewById(R.id.textViewTotalOrder);

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //substract the current total price article (before update the text) from total order
            if(!quantityProduct.getText().toString().equals("")){
                //int quantityBeforeUpdate = Integer.parseInt(quantityProduct.getText().toString());
                float totalPriceBeforeUpdate = Float.parseFloat(totalPriceProduct.getText().toString());
                float currentTotalOrder = Float.parseFloat(totalOrder.getText().toString());
                float newTotalOrder = currentTotalOrder - totalPriceBeforeUpdate;
                totalOrder.setText(String.valueOf(newTotalOrder));
            }

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            //get the new total price article after the text changed in view quantityProduct
            if(!(quantityProduct.getText().toString().equals(""))){
                float factorPriceProduct = Float.parseFloat(priceProduct.getText().toString());
                float factorQuantityProduct = Float.parseFloat(s.toString());
                float moltiplicationTotalPrice =  factorPriceProduct * factorQuantityProduct;
                totalPriceProduct.setText(String.valueOf(moltiplicationTotalPrice));

                //total order updating adding new total price article
                float currentTotalOrder = Float.parseFloat(totalOrder.getText().toString());
                float newTotalOrder = currentTotalOrder + (moltiplicationTotalPrice);
                totalOrder.setText(String.valueOf(newTotalOrder));
            }else{
                totalPriceProduct.setText(String.valueOf(0.0));
            }
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
        holder.quantityProduct.setText(String.valueOf(listaProdotti.get(position).getQuantita()));

        //update total price article
        holder.totalPriceProduct.setText(String.valueOf((float) listaProdotti.get(position).getPrezzo()*listaProdotti.get(position).getQuantita()));

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
