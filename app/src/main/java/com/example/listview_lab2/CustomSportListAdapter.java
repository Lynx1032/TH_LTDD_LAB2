package com.example.listview_lab2;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CustomSportListAdapter extends ArrayAdapter<Integer> {
    private final Activity context;
    private final List<Integer> sportNames;
    private final List<Integer> imageIDs;

    public CustomSportListAdapter(Activity context, List<Integer> sportNames, List<Integer> imageIDs) {
        super(context, R.layout.list_view_row, sportNames);
        this.context = context;
        this.sportNames = sportNames;
        this.imageIDs = imageIDs;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_view_row, null, true);

        TextView nameTextField = rowView.findViewById(R.id.sport_Name_ID);
        ImageView imageView = rowView.findViewById(R.id.sport_Icon_ID);
        Button detailButton = rowView.findViewById(R.id.detail_btn);
        Button deleteButton = rowView.findViewById(R.id.delete_btn);
        Button modifyButton = rowView.findViewById(R.id.modify_btn);

        nameTextField.setText(context.getString(sportNames.get(position)));
        imageView.setImageResource(imageIDs.get(position));

        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                Resources res = context.getResources();
                String message = res.getString(sportNames.get(position));
                intent.putExtra("sport", message);
                context.startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sportNames.remove(position);
                imageIDs.remove(position);
                notifyDataSetChanged();
            }
        });

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showModifyDialog(position);
            }
        });

        return rowView;
    }

    private void showModifyDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Modify Info");

        // Inflate the dialog layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.modify_dialog, null);
        builder.setView(dialogView);

        // Get references to dialog views
        final EditText nameEditText = dialogView.findViewById(R.id.editTextName);
        final ImageView imageView = dialogView.findViewById(R.id.imageView);

        // Set initial values
        nameEditText.setText(context.getString(sportNames.get(position)));
        imageView.setImageResource(imageIDs.get(position));

        // Pick image button click listener
        Button pickImageButton = dialogView.findViewById(R.id.buttonPickImage);
        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromStorage(imageView);
            }
        });

        // Set up the dialog buttons
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Update the values in the list
                String newName = nameEditText.getText().toString();
                sportNames.set(position, context.getResources().getIdentifier(newName, "string", context.getPackageName()));
                imageIDs.set(position, getImageResourceId(imageView));

                notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void pickImageFromStorage(ImageView targetImageView) {
        // Implement your image picking logic here
    }

    private int getImageResourceId(ImageView imageView) {
        // Implement your logic to retrieve the resource ID of the image set in the ImageView
        return 0; // Replace 0 with the actual resource ID
    }

    private Bitmap decodeSampledBitmapFromUri(Uri uri, int reqWidth, int reqHeight) throws IOException {
        InputStream stream = context.getContentResolver().openInputStream(uri);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(stream, null, options);
        stream.close();

        int scale = 1;
        while ((options.outWidth / scale) > reqWidth || (options.outHeight / scale) > reqHeight) {
            scale *= 2;
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = scale;
        stream = context.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);
        stream.close();

        return bitmap;
    }
}