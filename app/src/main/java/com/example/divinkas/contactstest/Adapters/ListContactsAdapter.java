package com.example.divinkas.contactstest.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

public class ListContactsAdapter extends RecyclerView.Adapter<ListContactsAdapter.ViewHolder>{
    private Context context;
    private LayoutInflater inflater;
    private List<ContactItem> contactItemList;
    private SQLiteDatabase db;
    private String comment;

    public ListContactsAdapter(Context context, List<ContactItem> lst){
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        contactItemList = lst;

        LocalDB_Like_contacts dbHelper = new LocalDB_Like_contacts(context);
        db = dbHelper.getWritableDatabase();
        comment = "Коментарій відсутній.";
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.contact_load_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String fullName = contactItemList.get(position).getLast_name() + " " + contactItemList.get(position).getFirst_name();
        holder.pib.setText(fullName);
        holder.misce_rob.setText(contactItemList.get(position).getMisce_rob());

        if(IsMyLike(position)){
            holder.addToLike.setImageResource(R.drawable.star_contact);
            holder.addToLike.setTag(context.getString(R.string.like_1));
        }
        holder.addToLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = context.getString(R.string.like);
                if(holder.addToLike.getTag().equals(s)){
                    initDialogAddComment(holder, position);
                }
                if(holder.addToLike.getTag().equals(context.getString(R.string.like_1))){
                    initDialogDellComponent(holder, position);
                }

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
    }

    private boolean IsMyLike(int position){
        String id = contactItemList.get(position).getId();
        Cursor cursor = db.rawQuery(" select * from " + LocalDB_Like_contacts.TABLE_CONTACTS_LIKE
                    + " where " + LocalDB_Like_contacts.CONTACT_IDENTIFY + " =?", new String[]{id});
        boolean isLike =  cursor.getCount()!=0;
        cursor.close();
        return isLike;
    }

    private void initDialogDellComponent(@NonNull final ViewHolder holder, final int position){
        View view = inflater.inflate(R.layout.dell_dialog, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                .setTitle("Видалити з обраних:")
                .setView(view);
        dialog.setPositiveButton(R.string.like_1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String id = contactItemList.get(position).getId();
                String wr = " =?";
                db.delete(LocalDB_Like_contacts.TABLE_CONTACTS_LIKE,
                        LocalDB_Like_contacts.CONTACT_IDENTIFY + wr, new String[]{id});
                holder.addToLike.setImageResource(R.drawable.star_outline_contact);
                holder.addToLike.setTag(context.getString(R.string.like));

            }
        });
        dialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
    }

    private void initDialogAddComment(@NonNull final ViewHolder holder, final int position){
        View view = inflater.inflate(R.layout.comment_dialog_fragment_edit_text_layout, null);
        final EditText etCommennt = view.findViewById(R.id.etCommentContact);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                .setTitle("Додайте коментарій:")
                .setView(view);
        dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newComment = comment;
                if(!etCommennt.getText().toString().isEmpty()){
                    newComment = etCommennt.getText().toString();
                }

                String id = contactItemList.get(position).getId();
                Cursor cursor = db.rawQuery("select * from " + LocalDB_Like_contacts.TABLE_CONTACTS_LIKE + " where "
                    + LocalDB_Like_contacts.CONTACT_IDENTIFY + " =?", new String[]{id});

                if (cursor.getCount() == 0) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(LocalDB_Like_contacts.CONTACT_IDENTIFY, id);
                    contentValues.put(LocalDB_Like_contacts.CONTACT_FIRST_NAME, contactItemList.get(position).getFirst_name());
                    contentValues.put(LocalDB_Like_contacts.CONTACT_LAST_NAME, contactItemList.get(position).getLast_name());
                    contentValues.put(LocalDB_Like_contacts.CONTACT_MISCE_ROB, contactItemList.get(position).getMisce_rob());
                    contentValues.put(LocalDB_Like_contacts.CONTACT_LINK_PDF, contactItemList.get(position).getLink_pdf());
                    contentValues.put(LocalDB_Like_contacts.CONTACT_POSADA, contactItemList.get(position).getId());
                    contentValues.put(LocalDB_Like_contacts.CONTACT_COMMENT, newComment);
                    db.insert(LocalDB_Like_contacts.TABLE_CONTACTS_LIKE, null, contentValues);
                }
                else{
                    Toast.makeText(context, "Контакт вже доданий до обраних", Toast.LENGTH_SHORT).show();
                }
                holder.addToLike.setImageResource(R.drawable.star_contact);
                holder.addToLike.setTag(context.getString(R.string.like_1));
                cursor.close();
            }
        });
        dialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // cancel
            }
        });
        dialog.show();
    }

    @Override
    public int getItemCount() { return contactItemList.size(); }

    @Override
    public int getItemViewType(int position) { return position; }

    @Override
    public long getItemId(int position) { return position; }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView pib, misce_rob;
        ImageView addToLike, openPDF;

        public ViewHolder(View itemView) {
            super(itemView);
                pib = itemView.findViewById(R.id.tvPIB);
                misce_rob = itemView.findViewById(R.id.tvMisceName);
                addToLike = itemView.findViewById(R.id.addToLikeContact);
                openPDF = itemView.findViewById(R.id.openPDF);

        }
    }
}
