package com.gmail.davidecoffaro.productscity.utilclass;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.davidecoffaro.productscity.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.URL;
import java.util.List;

public class RVBuyProductListAdapter extends RecyclerView.Adapter<RVBuyProductListAdapter.ProductViewHolder> {

    public static class ProductViewHolder extends RecyclerView.ViewHolder implements TextWatcher, View.OnFocusChangeListener {
        CardView cv;
        TextView nameProduct;
        TextView descriptionProduct;
        TextView priceProduct;
        ImageView imageProduct;
        EditText quantityProduct;
        TextView totalPriceProduct;

        //total order view management
        TextView totalOrder;

        //reference to Prodotto in listaProdotti, to update its quantity
        Prodotto prodotto;

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

            //set listener when editText loses its focus -> to close the keyboard
            quantityProduct.setOnFocusChangeListener(this);

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

                //update quantity in prodotto variable
                if(prodotto!=null){
                    prodotto.setQuantita((int)factorQuantityProduct);
                }

            }else{
                totalPriceProduct.setText(String.valueOf(0.0));
            }
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(v==quantityProduct && !hasFocus ){
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
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

        holder.prodotto = listaProdotti.get(position);

        String urlImage = listaProdotti.get(position).getUrlimmagine();
        //TODO translate url to image
        //holder.imageProduct.setImageResource(listaProdotti.get(position).getImmagine());

        //holder.quantityProduct.setText(listaProdotti.get(position).getQuantity());
        //holder.quantityProduct.setText(listaProdotti.get(position).getTotalPrice());

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
