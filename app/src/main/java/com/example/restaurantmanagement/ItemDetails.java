package com.example.restaurantmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ItemDetails extends AppCompatActivity {
    String orderId,order_price,tableno,order_status;
    RecyclerView recycler_item;
    DatabaseReference reference;
    ProgressDialog progressDialog;
    RelativeLayout rl_btm;
    ItemRecyclerAdapter adapter;
    TextView tv_title,tv_all_cost,tv_tanleno;
    ArrayList<Item_Model> item_modelArrayList=new ArrayList<>();
    TextView txt_bill,txt_payment;
    private static final int STORAGE_CODE=1000;
    String currentDateandTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        orderId=getIntent().getStringExtra("Order_id");
        order_price=getIntent().getStringExtra("order_cost");
        tableno=getIntent().getStringExtra("Table_no");
        order_status=getIntent().getStringExtra("Order_status");


        tv_title=findViewById(R.id.tv_title);
        tv_tanleno=findViewById(R.id.tv_tanleno);
        tv_all_cost=findViewById(R.id.tv_all_cost);
        rl_btm=findViewById(R.id.rl_btm);
        txt_bill=findViewById(R.id.txt_bill);
        txt_payment=findViewById(R.id.txt_payment);

        recycler_item=findViewById(R.id.recycler_item);
        recycler_item.setHasFixedSize(true);
        recycler_item.setLayoutManager(new LinearLayoutManager(this));
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCanceledOnTouchOutside(false);
        reference= FirebaseDatabase.getInstance().getReference().child("Order");
        tv_title.setText("Item Details "+"("+LoginPage.type+")");
        tv_all_cost.setText("Total Price :"+order_price);
        tv_tanleno.setText("Table No :"+tableno);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        currentDateandTime= sdf.format(new Date());



        Toast.makeText(ItemDetails.this, "Order Status "+order_status, Toast.LENGTH_SHORT).show();

        if(LoginPage.type.contentEquals("waiter"))
        {
            if(order_status.contentEquals("Complete"))
            {
                rl_btm.setVisibility(View.VISIBLE);
            }
        }



        showitems();

        txt_bill.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

               // Toast.makeText(ItemDetails.this, "Pdf will open", Toast.LENGTH_SHORT).show();
                if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                            PackageManager.PERMISSION_DENIED)
                    {
                        String[] parmission={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(parmission,STORAGE_CODE);
                    }
                    else
                    {
                        Saved_pdf();
                    }
                }
                else
                {
                    Saved_pdf();
                }
            }
        });

        txt_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ItemDetails.this,PaymentPage.class);
                intent.putExtra("order_cost",order_price);
                intent.putExtra("Order_id",orderId);
                startActivity(intent);
            }
        });
    }

    private void showitems() {
        progressDialog.setMessage("Loading Orders...");
        progressDialog.show();
        reference.child(orderId).child("Item").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                item_modelArrayList.clear();
                for (DataSnapshot ds:snapshot.getChildren())
                {
                    Log.d("jdhjd","dghdg");
                    Item_Model model=ds.getValue(Item_Model.class);
                    item_modelArrayList.add(model);

                }

                prepard();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(ItemDetails.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    private void Saved_pdf() {
        Document document=new Document();
        String mfile_name=new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        String mfile_path= Environment.getExternalStorageDirectory() +  "/"  + mfile_name + ".pdf";


        try {
            PdfWriter pdfWriter= PdfWriter.getInstance(document,new FileOutputStream(mfile_path));
            document.open();
            PdfContentByte cb = pdfWriter.getDirectContent();

            Font fontColour_black =  FontFactory.getFont(FontFactory.TIMES, 18f, Font.BOLD, BaseColor.BLACK);
            Font fontColour_black2 =  FontFactory.getFont(FontFactory.TIMES, 22f, Font.BOLD, BaseColor.BLACK);
            Font fontColour_black1 =  FontFactory.getFont(FontFactory.TIMES, 18f, Font.NORMAL, BaseColor.BLACK);
            Font fontColour_red =  FontFactory.getFont(FontFactory.TIMES, 18f, Font.BOLD, BaseColor.RED);
            document.setMargins(0,0,0,0);

            Paragraph title=new Paragraph("Mother's Hut Restaurant",fontColour_black);

            title.setAlignment(Paragraph.ALIGN_CENTER);
            Paragraph title2=new Paragraph("93,Girish Ghosal Road-Naihati");
            title2.setAlignment(Paragraph.ALIGN_CENTER);

            Paragraph title3=new Paragraph(" Contact-No : 8910390599");
            title3.setAlignment(Paragraph.ALIGN_CENTER);

            Paragraph title4=new Paragraph(" TAX INVOICE\n\n\n",fontColour_black);
            title4.setAlignment(Paragraph.ALIGN_CENTER);

            Paragraph title5=new Paragraph(""+currentDateandTime,fontColour_black1);
            title5.setAlignment(Paragraph.ALIGN_LEFT);

            Paragraph title6=new Paragraph("Order Id :"+orderId,fontColour_black1);
            title6.setAlignment(Paragraph.ALIGN_LEFT);

            Paragraph title7=new Paragraph("Order Status :"+order_status,fontColour_black1);
            title7.setAlignment(Paragraph.ALIGN_LEFT);

            Paragraph title8=new Paragraph("Table No :"+tableno,fontColour_black1);
            title8.setAlignment(Paragraph.ALIGN_LEFT);



            document.add(title);
            document.add(title2);
            document.add(title3);
            document.add(title4);
            document.add(title5);
            document.add(title6);
            document.add(title7);
            document.add(title8);

            Paragraph title9=new Paragraph("\n\n",fontColour_black1);
            title9.setAlignment(Paragraph.ALIGN_CENTER);

            document.add(title9);





            float columnwidth[]={300f,300f,300F,300F};
            PdfPTable table=new PdfPTable(columnwidth);
            table.addCell("Item Name");
            table.addCell("Qty");
            table.addCell("MRP");

            table.addCell("AMT");

            for(int i=0;i<item_modelArrayList.size();i++)
            {
                table.addCell(item_modelArrayList.get(i).getItemname());
                table.addCell(item_modelArrayList.get(i).getItemquantity());
                table.addCell(item_modelArrayList.get(i).getTotalprice());
                table.addCell(String.valueOf((Double.parseDouble(item_modelArrayList.get(i).getTotalprice()))*(Double.parseDouble(item_modelArrayList.get(i).getItemquantity()))));

            }

            document.add(table);
            Paragraph title10=new Paragraph("\n\n\n\n",fontColour_black1);
            title10.setAlignment(Paragraph.ALIGN_CENTER);

            document.add(title10);


            Paragraph title11=new Paragraph("Total Price :"+order_price,fontColour_black2);
            title11.setAlignment(Paragraph.ALIGN_RIGHT);

            document.add(title11);

            Paragraph title12=new Paragraph("\n\n\n\n\n",fontColour_black1);
            title12.setAlignment(Paragraph.ALIGN_CENTER);

            document.add(title12);

            Paragraph title13=new Paragraph("Thank You. Come Again!!",fontColour_black2);
            title13.setAlignment(Paragraph.ALIGN_CENTER);

            document.add(title13);


            document.close();
            Toast.makeText(this, mfile_name +".pdf\n is saved  to\n"+mfile_path, Toast.LENGTH_SHORT).show();



        }
        catch (Exception e)
        {

        }
    }

    private void prepard() {
        adapter=new ItemRecyclerAdapter(item_modelArrayList, ItemDetails.this);
        recycler_item.setAdapter(adapter);
        progressDialog.dismiss();
    }
}