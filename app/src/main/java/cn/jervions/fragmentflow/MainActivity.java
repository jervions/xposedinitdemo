package cn.jervions.fragmentflow;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{

    private ContentFragment mContentFragment;
    public static String blankspace = "";

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String bs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("test", Activity.MODE_WORLD_READABLE);
        editor = sp.edit();

        findViewById(R.id.btnAdd).setOnClickListener(this);
        findViewById(R.id.btnSub).setOnClickListener(this);
        findViewById(R.id.btnReboot).setOnClickListener(this);

        findViewById(R.id.btnBG).setOnClickListener(this);

        findViewById(R.id.btnAddR).setOnClickListener(this);
        findViewById(R.id.btnSubR).setOnClickListener(this);


    }

    public void execCommand(){
        Process reboot = null;
        try{
            Runtime.getRuntime().exec("su");
            reboot=Runtime.getRuntime().exec("reboot");
            reboot.waitFor();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnAdd:
                //blankspace = "       " + blankspace;
                editor.putString("test_put", "1");
                bs = sp.getString("test_blank_space"," ");
                bs = " " + bs;
                editor.putString("test_blank_space", bs);
                editor.commit();
                //Toast.makeText(this,sp.getString("test_put","0"),Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnAddR:
                editor.putString("test_put", "1");
                bs = sp.getString("test_blank_space_r"," ");
                bs = bs + " ";
                editor.putString("test_blank_space_r", bs);
                editor.commit();
                break;

            case  R.id.btnSub:
                //blankspace = blankspace.substring(7,blankspace.length());
                editor.putString("test_put", "2");
                bs = sp.getString("test_blank_space"," ");
                if (bs.length()>1){
                    bs = bs.substring(1,bs.length());
                    editor.putString("test_blank_space", bs);
                }

                editor.commit();
                //Toast.makeText(this,sp.getString("test_put","0"),Toast.LENGTH_SHORT).show();
                break;
            case  R.id.btnSubR:
                //blankspace = blankspace.substring(7,blankspace.length());
                editor.putString("test_put", "2");
                bs = sp.getString("test_blank_space_r"," ");
                if (bs.length()>1){
                    bs = bs.substring(0,bs.length()-1);
                    editor.putString("test_blank_space_r", bs);
                }

                editor.commit();
                Toast.makeText(this,sp.getString("test_put","0"),Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnReboot:
                execCommand();
                break;
            case R.id.btnBG:
                Toast.makeText(this,sp.getString("test_put","0") + "\n" +sp.getString("test_blank_space"," ").length()+ "\n" +sp.getString("test_blank_space_r"," ").length() ,Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
