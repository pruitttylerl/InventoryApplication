package com.snhu.cs360.inventoryapplication2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class DisplayOneFragment extends Fragment {
    private static final List<ItemListener> listeners = new ArrayList<>();

    public interface ItemListener {
        void handleEdited(Item original, Item edited);
        void handleDeleted(Item deleted);
    }

    private Item item;

    public static DisplayOneFragment newInstance(int itemId) {
        DisplayOneFragment fragment = new DisplayOneFragment();
        Bundle args = new Bundle();
        args.putInt("itemId", itemId);
        fragment.setArguments(args);
        return fragment;
    }
    public static void register(ItemListener listener) { listeners.add(listener); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the item ID from the intent that started DisplayOneActivity
        int itemId = 1;
        if (getArguments() != null) {
            itemId = getArguments().getInt("itemId");
        }

        item = ItemsDatabase.getInstance(getContext()).getItem(itemId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_one, container, false);

        TextView itemNameTextView = (TextView) view.findViewById(R.id.itemName);
        itemNameTextView.setText(item.getName());

        TextView itemQuantityTextView = (TextView) view.findViewById(R.id.itemQuantity);
        itemQuantityTextView.setText(Integer.toString(item.getQuantity()));

        TextView itemUnitsTextView = (TextView) view.findViewById(R.id.itemUnits);
        itemUnitsTextView.setText(item.getUnits());

        TextView itemValueTextView = (TextView) view.findViewById(R.id.itemValue);
        itemValueTextView.setText(Integer.toString(item.getValue()));

        TextView itemLocationTextView = (TextView) view.findViewById(R.id.itemLocation);
        itemLocationTextView.setText(item.getLocation());

        Button btnEdit = view.findViewById(R.id.btnEdit);

        btnEdit.setOnClickListener(l -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Edit Item Details");

            View editView = inflater.inflate(R.layout.item_display, null);
            builder.setView(editView);

            EditText txtItemName = editView.findViewById(R.id.txtItemName);
            txtItemName.setText(item.getName());

            EditText txtItemQuantity = editView.findViewById(R.id.txtItemQuantity);
            txtItemQuantity.setText(Integer.toString(item.getQuantity()));

            EditText txtItemUnits = editView.findViewById(R.id.txtItemUnits);
            txtItemUnits.setText(item.getUnits());

            EditText txtItemValue = editView.findViewById(R.id.txtItemValue);
            txtItemValue.setText(Integer.toString(item.getValue()));

            EditText txtItemLocation = editView.findViewById(R.id.txtItemLocation);
            txtItemLocation.setText(item.getLocation());

            builder.setPositiveButton("Ok", (dialogInterface, i) -> {
                String name = txtItemName.getText().toString();
                int quantity = Integer.parseInt(txtItemQuantity.getText().toString());
                String units = txtItemUnits.getText().toString();
                int value = Integer.parseInt(txtItemValue.getText().toString());
                String location = txtItemLocation.getText().toString();

                Item edited = new Item(item.getId(), name, quantity, units, value, location);

                boolean isEdited = ItemsDatabase.getInstance(getContext()).editItem(item.getId(), edited);

                if (!isEdited) {
                    Toast.makeText(getContext(), "Error Editing Item", Toast.LENGTH_SHORT).show();
                    //FIXME: DisplayOneFragment does not update fragment_display_one.xml until revisiting the fragment
                } else {
                    listeners.forEach(listener -> listener.handleEdited(item, edited));
                    item = edited;
                    txtItemName.setText(item.getName());
                    txtItemQuantity.setText(Integer.toString(item.getQuantity()));
                    txtItemUnits.setText(item.getUnits());
                    txtItemValue.setText(Integer.toString(item.getValue()));
                    txtItemLocation.setText(item.getLocation());
                }
            });

            builder.setNegativeButton("Cancel", null);
            builder.create();

            builder.show();
        });

        Button btnDelete = view.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(l -> {
            boolean isDeleted = ItemsDatabase.getInstance(getContext()).deleteItem(item.getId());

            if (!isDeleted) {
                Toast.makeText(getContext(), "Error Deleting Item", Toast. LENGTH_SHORT).show();
            } else {
                listeners.forEach(listener -> listener.handleDeleted(item));
                getActivity().onBackPressed();
            }
        });

        return view;
    }
}
