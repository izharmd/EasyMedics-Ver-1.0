package FragmentView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wakeup.xxx.R;

/**
 * Created by BWS on 21/07/2018.
 */

public class DiagonosisBoardFragment extends Fragment {
    View rootview;
    ImageView imv_header_back,imv_header;
    TextView text_headerMain,text_header;
    LinearLayout ll_header;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_diagonosis_board, viewGroup, false);

       initView();

        return rootview;
    }

    private void initView() {


        /*imv_header = (ImageView)rootview.findViewById(R.id.imv_header);
        imv_header_back = (ImageView)rootview.findViewById(R.id.imv_header_back);
       // imv_header_back.setVisibility(View.GONE);
        text_headerMain = (TextView)rootview.findViewById(R.id.text_headerMain);
        text_header = (TextView)rootview.findViewById(R.id.text_header);

        imv_header.setImageResource(R.drawable.ic_quary);
        text_headerMain.setText("Your DIAGNOSIS BOARD");
        text_header.setText("CONDITION");*/
    }
}