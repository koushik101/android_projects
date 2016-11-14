package com.example.koushik.galop;

        import android.graphics.Bitmap;
        import android.graphics.Color;
        import android.graphics.drawable.BitmapDrawable;
        import android.support.v7.app.AppCompatActivity;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.content.Intent;
        import android.database.Cursor;
        import android.graphics.BitmapFactory;
        import android.widget.Toast;
        import android.net.Uri;
        import android.provider.MediaStore;
        import android.os.Bundle;
        import java.math.*;
        import java.lang.*;
        import android.content.Context;
        import android.graphics.drawable.Drawable;
public class MainActivity extends AppCompatActivity{
    Button b1, b2, b3;
    ImageView im,iv;


    private Bitmap bmp,bmp2;
    private Bitmap operation;
    private static int RESULT_LOAD_IMAGE = 1;
    String imageDecodableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        im = (ImageView) findViewById(R.id.imageView1);
        iv = (ImageView) findViewById(R.id.imageView2);
        BitmapDrawable abmp = (BitmapDrawable) im.getDrawable();
        BitmapDrawable abmp2 = (BitmapDrawable) iv.getDrawable();
        bmp = abmp.getBitmap();
        bmp2 = abmp2.getBitmap();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMAGE  &&  resultCode == RESULT_OK && null != data ){
                Uri selectimage = data.getData();
                String[] filePathColumn =  {MediaStore.Images.Media.DATA};
                Cursor cursor =   getContentResolver().query(selectimage,filePathColumn,null,null,null);
                cursor.moveToFirst();
                int columnIndex =  cursor.getColumnIndex(filePathColumn[0]);
                imageDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imageView = (ImageView)findViewById(R.id.imageView1);
                imageView.setImageBitmap(BitmapFactory.decodeFile(imageDecodableString));

            }
            else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        }catch  (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void loadImagefromGallery(View view){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
    }
    public void saveImage(){
        MediaStore.Images.Media.insertImage(getContentResolver(), operation,"appArt.jpeg"  , null);
    }
    public void gray(View view) {
        operation = Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(), bmp.getConfig());
        double red = 0.33;
        double green = 0.59;
        double blue = 0.11;

        for (int i = 0; i < bmp.getWidth(); i++) {
            for (int j = 0; j < bmp.getHeight(); j++) {
                if(i+2<bmp.getWidth() && j+2<bmp.getHeight()) {
                    int p11 = bmp.getPixel(i, j);
                    int p12 = bmp.getPixel(i + 1, j);
                    int p13 = bmp.getPixel(i + 2, j);
                    int p21 = bmp.getPixel(i, j + 1);
                    int p22 = bmp.getPixel(i + 1, j + 1);
                    int p23 = bmp.getPixel(i + 2, j + 1);
                    int p31 = bmp.getPixel(i, j + 2);
                    int p32 = bmp.getPixel(i + 1, j + 2);
                    int p33 = bmp.getPixel(i + 2, j + 2);
                    int r11 = Color.red(p11);
                    int g11 = Color.green(p11);
                    int b11 = Color.blue(p11);
                    int r12 = Color.red(p12);
                    int g12 = Color.green(p12);
                    int b12 = Color.blue(p12);
                    int r13 = Color.red(p13);
                    int g13 = Color.green(p13);
                    int b13 = Color.blue(p13);
                    int r21 = Color.red(p21);
                    int g21 = Color.green(p21);
                    int b21 = Color.blue(p21);
                    int r22 = Color.red(p22);
                    int g22 = Color.green(p22);
                    int b22 = Color.blue(p22);
                    int r23 = Color.red(p23);
                    int g23 = Color.green(p23);
                    int b23 = Color.blue(p23);
                    int r31 = Color.red(p31);
                    int g31 = Color.green(p31);
                    int b31 = Color.blue(p31);
                    int r32 = Color.red(p32);
                    int g32 = Color.green(p32);
                    int b32 = Color.blue(p32);
                    int r33 = Color.red(p33);
                    int g33 = Color.green(p33);
                    int b33 = Color.blue(p33);
                    r22 = (r22 * 9 - r11 - r12 - r13 - r21 - r23 - r31 - r32 - r33) ;
                    g22 = (g22 * 9 - g11 - g12 - g13 - g21 - g23 - g31 - g32 - g33) ;
                    b22 = (b22 * 9 - b11 - b12 - b13 - b21 - b23 - b31 - b32 - b33) ;

                    // r = (int) red * r;
                    // g = (int) green * g;
                    // b = (int) blue * b;
                    operation.setPixel(i + 2, j + 2, Color.argb(Color.alpha(p22), r22, g22, b22));
                }
            }
        }
        im.setImageBitmap(operation);
    }

    public void bright(View view){
        operation= Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(),bmp.getConfig());
        for(int i=0; i<bmp.getWidth(); i++){
            for(int j=0; j<bmp.getHeight(); j++) {
                int p = bmp.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                if(b>=255){b=b;}else {
                    b = (int) (b * (1 + 0.67));
                }
                operation.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));
            }
        }

      //  int count1 = 1,count2 = 1,count3 = 1,count4 = 1,count5 = 1,count6 = 1;
      //  double md1 = 0,md2 = 0,md3 = 0,md4 = 0,md5 = 0,md6 = 0; int f2r1 = 0,f2g1 = 0,f2b1 = 0,fmx1 = 0,fmy1 = 0,fmx2 = 0,fmy2 = 0,fmx3 = 0,fmy3 = 0,fmx4 = 0,fmy4 = 0,fmx5 = 0,fmy5 = 0,fmx6=0,fmy6 = 0;
      //  int f2r2 = 0,f2g2 = 0,f2b2 = 0,f2r3 = 0,f2g3 = 0,f2b3 = 0,f2r4 = 0,f2g4 = 0,f2b4 = 0,f2r5 = 0,f2g5 = 0,f2b5 = 0,f2r6 = 0,f2g6 = 0,f2b6 = 0;
       // int mx1 = (int)(Math.random()*(bmp.getWidth()-0));
       // int my1 = (int)(Math.random()*(bmp.getHeight()-0));
       // int mx2 = (int)(Math.random()*(bmp.getWidth()-0));
       // int my2 = (int)(Math.random()*(bmp.getHeight()-0));
       // int mx3 = (int)(Math.random()*(bmp.getWidth()-0));
       // int my3 = (int)(Math.random()*(bmp.getHeight()-0));
       // int mx4 = (int)(Math.random()*(bmp.getWidth()-0));
       // int my4 = (int)(Math.random()*(bmp.getHeight()-0));
       // int mx5 = (int)(Math.random()*(bmp.getWidth()-0));
       // int my5 = (int)(Math.random()*(bmp.getHeight()-0));
       // int mx6 = (int)(Math.random()*(bmp.getWidth()-0));
       // int my6 = (int)(Math.random()*(bmp.getHeight()-0));
       // double k[][] = new double[bmp.getWidth()][bmp.getHeight()];

      /*  for(int s=0;s<10;s++){
            count1 = 1;
            count2 = 1;
            count3 = 1;
            count4 = 1;
            count5 = 1;
            count6 = 1;
            f2r1 = 0;f2g1 = 0;f2b1 = 0;
            f2r2 = 0;f2g2 = 0;f2b2 = 0;f2r3 = 0;f2g3 = 0;f2b3 = 0;f2r4 = 0;f2g4 = 0;f2b4 = 0;f2r5 = 0;f2g5 = 0;f2b5 = 0;f2r6 = 0;f2g6 = 0;f2b6 = 0;
            fmx1 = 0;
            fmy1 = 0;
            fmx2 = 0;
            fmy2 = 0;
            fmx3 = 0;
            fmy3 = 0;
            fmx4 = 0;
            fmy4 = 0;
            fmx5 = 0;
            fmy5 = 0;
            fmx6=0;
            fmy6 = 0;
            */
        /*
            for (int i = 0; i < bmp.getWidth(); i++){
                for (int j = 0; j < bmp.getHeight(); j++){*/
              //      md1 = /*Math.abs(*/Math.sqrt((mx1 - i) * (mx1 - i) + (my1 - j) * (my1 - j));
              //      md2 = /*Math.abs(*/Math.sqrt((mx2 - i) * (mx2 - i) + (my2 - j) * (my2 - j));
              //      md3 = /*Math.abs(*/Math.sqrt((mx3 - i) * (mx3 - i) + (my3 - j) * (my3 - j));
              //      md4 = /*Math.abs(*/Math.sqrt((mx4 - i) * (mx4 - i) + (my4 - j) * (my4 - j));
              //      md5 = /*Math.abs(*/Math.sqrt((mx5 - i) * (mx5 - i) + (my5 - j) * (my5 - j));
              //      md6 = /*Math.abs(*/Math.sqrt((mx6 - i) * (mx6 - i) + (my6 - j) * (my6 - j));
                /*    if (md1 > md2 && md1 > md3 && md1 > md4 && md1 > md5 && md1 > md6) {
                        k[i][j] = md1;

                        f2r1 = f2r1 + Color.red(bmp.getPixel(i, j));
                        f2g1 = f2g1 + Color.green(bmp.getPixel(i, j));
                        f2b1 = f2b1 + Color.blue(bmp.getPixel(i, j));
                    }
                    else if (md2 > md1 && md2 > md3 && md2 > md4 && md2 > md5 && md2 > md6) {
                        k[i][j] = md2;

                        f2r2 = f2r2 + Color.red(bmp.getPixel(i, j));
                        f2g2 = f2g2 + Color.green(bmp.getPixel(i, j));
                        f2b2 = f2b2 + Color.blue(bmp.getPixel(i, j));
                    }
                    else if (md3 > md2 && md3 > md1 && md3 > md4 && md3 > md5 && md3 > md6) {
                        k[i][j] = md3;

                        f2r3 = f2r3 + Color.red(bmp.getPixel(i, j));
                        f2g3 = f2g3 + Color.green(bmp.getPixel(i, j));
                        f2b3 = f2b3 + Color.blue(bmp.getPixel(i, j));
                    }
                    else if (md4 > md2 && md4 > md3 && md4 > md1 && md4 > md5 && md4 > md6) {
                        k[i][j] = md4;

                        f2r4 = f2r4 + Color.red(bmp.getPixel(i, j));
                        f2g4 = f2g4 + Color.green(bmp.getPixel(i, j));
                        f2b4 = f2b4 + Color.blue(bmp.getPixel(i, j));
                    }
                    else if (md5 > md2 && md5 > md3 && md5 > md4 && md5 > md1 && md5 > md6) {
                        k[i][j] = md5;

                        f2r5 = f2r5 + Color.red(bmp.getPixel(i, j));
                        f2g5 = f2g5 + Color.green(bmp.getPixel(i, j));
                        f2b5 = f2b5 + Color.blue(bmp.getPixel(i, j));
                    }
                    else if (md6 > md2 && md6 > md3 && md6 > md4 && md6 > md5 && md6 > md1) {
                        k[i][j] = md6;

                        f2r6 = f2r6 + Color.red(bmp.getPixel(i, j));
                        f2g6 = f2g6 + Color.green(bmp.getPixel(i, j));
                        f2b6 = f2b6 + Color.blue(bmp.getPixel(i, j));
                    }
                    if (s > 1){
                        if(k[i][j]==md1){

                            fmx1 =  fmx1 + i;
                            fmy1 = fmy1 + j;
                            count1++;
                        }
                        if(k[i][j]==md2){

                            fmx2 =  fmx2 + i;
                            fmy2 = fmy2 + j;
                            count2++;
                        }
                        if(k[i][j]==md3){

                            fmx3 =  fmx3 + i;
                            fmy3 = fmy3 + j;
                            count3++;
                        }
                        if(k[i][j]==md4){

                            fmx4 =  fmx4 + i;
                            fmy4 = fmy4 + j;
                            count4++;
                        }
                        if(k[i][j]==md5){

                            fmx5 =  fmx5 + i;
                            fmy5 = fmy5 + j;
                            count5++;
                        }
                        if(k[i][j]==md6){

                            fmx6 =  fmx6 + i;
                            fmy6 = fmy6 + j;
                            count6++;
                        }
                    }

                }

            }
            mx1 = fmx1/count1;
            my1 = fmy1/count1;
            mx2 = fmx2/count2;
            my2 = fmy2/count2;
            mx3 = fmx3/count3;
            my3 = fmy3/count3;
            mx4 = fmx4/count4;
            my4 = fmy4/count4;
            mx5 = fmx5/count5;
            my5 = fmy5/count5;
            mx6 = fmx6/count6;
            my6 = fmy6/count6;


        }
        for (int i = 0; i < bmp.getWidth(); i++) {
            for (int j = 0; j < bmp.getHeight(); j++) {
                int p = bmp.getPixel(i, j);
                if(k[i][j]==md1){
                    int r = f2r1/count1;
                    int g = f2g1/count1;
                    int b = f2b1/count1;
                    int alpha = Color.alpha(p);
                    operation.setPixel(i, j, Color.argb(alpha, r, g, b));

                }
                else if(k[i][j]==md2){
                    int r = f2r2/count2;
                    int g = f2g2/count2;
                    int b = f2b2/count2;
                    int alpha = Color.alpha(p);
                    operation.setPixel(i, j, Color.argb(alpha, r, g, b));


                }
                else if(k[i][j]==md3){
                    int r = f2r3/count3;
                    int g = f2g3/count3;
                    int b = f2b3/count3;
                    int alpha = Color.alpha(p);
                    operation.setPixel(i, j, Color.argb(alpha, r, g, b));
                }
                else if(k[i][j]==md4){
                    int r = f2r4/count4;
                    int g = f2g4/count4;
                    int b = f2b4/count4;
                    int alpha = Color.alpha(p);
                    operation.setPixel(i, j, Color.argb(alpha, r, g, b));
                }
                else if(k[i][j]==md5){
                    int r = f2r5/count5;
                    int g = f2g5/count5;
                    int b = f2b5/count5;
                    int alpha = Color.alpha(p);
                    operation.setPixel(i, j, Color.argb(alpha, r, g, b));
                }
                else if(k[i][j]==md6){
                    int r = f2r6/count6;
                    int g = f2g6/count6;
                    int b = f2b6/count6;
                    int alpha = Color.alpha(p);
                    operation.setPixel(i, j, Color.argb(alpha, r, g, b));
                }
            }
        }*/

        im.setImageBitmap(operation);

    }

    public void BlacknWhite(View view){
        operation= Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(),bmp.getConfig());

        for(int i=0; i<bmp.getWidth(); i++){
            for(int j=0; j<bmp.getHeight(); j++){
                int p = bmp.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int alpha = Color.alpha(p);
             if(r>127 || g>127 || b>127 ){
                 r = 255;
                 g = 255;
                 b = 255;
             }
                else{
                 r = 0;
                 b = 0;
                 g = 0;
             }
               // alpha = alpha -50;
                operation.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));
            }
        }
        im.setImageBitmap(operation);
    }

    public void blurr(View view) {
        operation = Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(),bmp.getConfig());

        for(int i=0; i<bmp.getWidth(); i++){
            for(int j=0; j<bmp.getHeight(); j++){
                if(i+10<bmp.getWidth() && j+10<bmp.getHeight()) {
                    int fr = 0;
                    int fg = 0;
                    int fb = 0;
                    int p55 = bmp.getPixel(i + 5, j + 5);
                   for(int k=0;k<=10;k++) {
                       for(int l=0;l<=10;l++) {
                           int p = bmp.getPixel(i + k, j + l);


                           int r = Color.red(p);
                           int g = Color.green(p);
                           int b = Color.blue(p);


                           fr = fr + r;
                           fg = fg + g;
                           fb = fb + b;

                           // r = (int) red * r;
                           // g = (int) green * g;
                           // b = (int) blue * b;

                       }
                   }
                    int r55 = fr/100;
                    int g55 = fg/100;
                    int b55 = fb/100;

                    operation.setPixel(i + 3, j + 2, Color.argb(Color.alpha(p55), r55, g55, b55));
                }

            }
        }
        im.setImageBitmap(operation);
    }

    public void green(View view){
        operation = Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(), bmp.getConfig());
        Bitmap bmp3 = Bitmap.createScaledBitmap(bmp2, bmp.getWidth(), bmp.getHeight(), true);
        Bitmap bmp1 = Bitmap.createScaledBitmap(bmp, bmp.getWidth()-500, bmp.getHeight()-600, true);
        int p,r,g,b,alpha;
        for(int i=0; i<bmp.getWidth(); i++){
            for(int j=0; j<bmp.getHeight(); j++){
                if(i<bmp3.getWidth()-250&&j<bmp3.getHeight()-300&&i>250&&j>300) {
                     p = bmp1.getPixel(i-250, j-300);
                     r = Color.red(p);
                     g = Color.green(p);
                     b = Color.blue(p);
                     alpha = Color.alpha(p);
                }
                else {

                     p = bmp3.getPixel(i, j);
                     r = Color.red(p);
                     g = Color.green(p);
                     b = Color.blue(p);
                     alpha = Color.alpha(p);
                }
                operation.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));
            }
        }
        im.setImageBitmap(operation);
    }

    public void only_red(View view){
        operation = Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(), bmp.getConfig());

        for(int i=0; i<bmp.getWidth(); i++){
            for(int j=0; j<bmp.getHeight(); j++){
                int p = bmp.getPixel(i, j);
                if((int)(Color.red(p))>127 && (int)(Color.blue(p))<127 && (int)(Color.green(p))<127)   {
                    int r = (int) (Color.red(p));
                    int b = (int) (Color.blue(p));
                    int g = (int) (Color.green(p));
                    int alpha = Color.alpha(p);
                    alpha = 0;
                    operation.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));

                }
                else {
                    int r = (int) (Color.red(p) * 0.299 + Color.green(p) * 0.587 + Color.blue(p) * 0.114);
                    int g = (int) (Color.red(p) * 0.299 + Color.green(p) * 0.587 + Color.blue(p) * 0.114);
                    int b = (int) (Color.red(p) * 0.299 + Color.green(p) * 0.587 + Color.blue(p) * 0.114);
                    int alpha = Color.alpha(p);
                    alpha = 0;
                    operation.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));

                }

            }
        }
        im.setImageBitmap(operation);
    }
}
