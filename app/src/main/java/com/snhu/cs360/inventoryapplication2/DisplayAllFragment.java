package com.snhu.cs360.inventoryapplication2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DisplayAllFragment extends Fragment implements DisplayOneFragment.ItemListener {

    private ItemAdapter adapter;

    @Override
    public void handleEdited(Item original, Item edited) { adapter.editItem(original, edited); }

    @Override
    public void handleDeleted(Item deleted) { adapter.deleteItem(deleted); }

    public interface OnItemSelectedListener {
        void onItemSelected(int itemId);
    }

    // Reference to the activity
    private OnItemSelectedListener listener;

    public DisplayAllFragment() { DisplayOneFragment.register(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnItemSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_all, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.item_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Send items to recycler view
        adapter = new ItemAdapter();
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.floating_action_button);

        fab.setOnClickListener(l -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            //Item display container
            View editView = inflater.inflate(R.layout.item_display, null);
            builder.setView(editView);
            builder.setTitle("Add Item");

            //Pulls EditText values
            EditText txtItemName = editView.findViewById(R.id.txtItemName);
            EditText txtItemQuantity = editView.findViewById(R.id.txtItemQuantity);
            EditText txtItemUnits = editView.findViewById(R.id.txtItemUnits);
            EditText txtItemValue = editView.findViewById(R.id.txtItemValue);
            EditText txtItemLocation = editView.findViewById(R.id.txtItemLocation);

            //Confirmed user input
            builder.setPositiveButton("ok", (dialogInterface, i) -> {

                String name = txtItemName.getText().toString();
                int quantity = Integer.parseInt(txtItemQuantity.getText().toString());
                String units = txtItemUnits.getText().toString();
                int value = Integer.parseInt(txtItemValue.getText().toString());
                String location = txtItemLocation.getText().toString();

                Item item = new Item(name, quantity, units, value, location);
                long result = ItemsDatabase.getInstance(getContext()).addItem(item);

                if (result == -1) {
                    Toast.makeText(getContext(), "Item already exists", Toast.LENGTH_SHORT).show();
                } else {
                    adapter.addItem(new Item(result, item));
                }
            });

            builder.setNegativeButton("Cancel", null);
            builder.create();

            builder.show();
        });

        return view;
    }

    private class ItemHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Item item;

        private TextView nameTextView;

        public ItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item, parent, false));
            itemView.setOnClickListener(this);
            nameTextView = itemView.findViewById(R.id.itemName);
        }

        public void bind(Item item) {
            this.item = item;
            nameTextView.setText(item.getName());
        }

        @Override
        public void onClick(View view) {
            // Tell ListActivity what item was clicked
            listener.onItemSelected((int)item.getId());
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

        private List<Item> items;

        public ItemAdapter() { items = ItemsDatabase.getInstance(getContext()).getItems(); }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            Item item = items.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void addItem(Item item){
            items.add(item);
            notifyItemInserted(items.size() - 1);
        }

        public void editItem(Item original, Item edited) {
            int index = items.indexOf(original);

            items.add(index, edited);
            items.remove(original);
            notifyItemChanged(index);
        }

        public void deleteItem(Item item) {
            int index = items.indexOf(item);
            items.remove(item);
            notifyItemRemoved(index);
        }
    }

}