package com.example.divinkas.contactstest.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.divinkas.contactstest.DTO.LocalDB_Like_contacts;
import com.example.divinkas.contactstest.Data.ContactItem;
import com.example.divinkas.contactstest.PDF_Rader_Activity;
import com.example.divinkas.contactstest.R;

import java.util.List;

public class ListContactsLikeAdapter extends RecyclerView.Adapter<ListContactsLikeAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<ContactItem> contactItemList;
    private LocalDB_Like_contacts dbHelper;
    private SQLiteDatabase db;

    public ListContactsLikeAdapter(Context context, List<ContactItem> list){
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        contactItemList = list;
        dbHelper = new LocalDB_Like_contacts(context);
        db = dbHelper.getWritableDatabase();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_like_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String fullName = contactItemList.get(position).getLast_name() + " " + contactItemList.get(position).getFirst_name();

        holder.pib.setText(fullName);
        holder.misce_rob.setText(contactItemList.get(position).getMisce_rob());
        holder.addToLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialogDellComponent(holder, position);
            }
        });
        holder.openPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PDF_Rader_Activity.class);
                intent.putExtra("link_pdf", contactItemList.get(position).getLink_pdf());
                context.startActivity(intent);
            }
        });

        holder.comment.setText(contactItemList.get(position).getComment());
        holder.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeValueComment(holder, position);
            }
        });

    }
    private void changeValueComment(ViewHolder holder, final int position){
        View view = inflater.inflate(R.layout.comment_dialog_fragment_edit_text_layout, null);
        final EditText et_comment = view.findViewById(R.id.etCommentContact);

        et_comment.setText(contactItemList.get(position).getComment());

        AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                .setTitle("Редагувати коментарій:")
                .setView(view);
        dialog.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!et_comment.getText().toString().isEmpty()){
                    String id = contactItemList.get(position).getId();
                    String newComment = et_comment.getText().toString();

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(LocalDB_Like_contacts.CONTACT_COMMENT, newComment);
                    db.update(LocalDB_Like_contacts.TABLE_CONTACTS_LIKE, contentValues,
                            LocalDB_Like_contacts.CONTACT_IDENTIFY + " =?", new String[]{id});

                    contactItemList.get(position).setComment(newComment);
                    notifyDataSetChanged();
                }
                else { Toast.makeText(context, "Введіть новий коментар!", Toast.LENGTH_SHORT).show(); }
            }
        });
        dialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // add initializaion
            }
        });
        dialog.show();
    }
    private void initDialogDellComponent(@NonNull final ListContactsLikeAdapter.ViewHolder holder, final int position){
        View view = inflater.inflate(R.layout.dell_dialog, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                .setTitle("Видалення:")
                .setView(view);
        dialog.setPositiveButton(R.string.like_1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String id = contactItemList.get(position).getId();
                String wr = " =?";
                db.delete(LocalDB_Like_contacts.TABLE_CONTACTS_LIKE,
                        LocalDB_Like_contacts.CONTACT_IDENTIFY + wr, new String[]{id});
                contactItemList.remove(contactItemList.get(position));
                notifyDataSetChanged();
            }
        });
        dialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
    }

    @Override
    public int getItemCount() { return contactItemList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView pib, misce_rob, comment;
        ImageView addToLike, openPDF, change;

        public ViewHolder(View itemView) {
            super(itemView);
            pib = itemView.findViewById(R.id.tvPIB_like);
            misce_rob = itemView.findViewById(R.id.tvMisceName_like);
            addToLike = itemView.findViewById(R.id.dell_from_LikeContact_like);
            openPDF = itemView.findViewById(R.id.openPDF_like);
            comment = itemView.findViewById(R.id.tv_comment_like);
            change = itemView.findViewById(R.id.img_change);

        }
    }
}
