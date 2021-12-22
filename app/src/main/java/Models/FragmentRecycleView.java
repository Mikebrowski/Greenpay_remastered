package Models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenpayremastered.R;

import org.w3c.dom.Text;

//THIS CLASS WILL USE A LIST INTO FRAGMENTS MAKES IT EASIER TO SEARCH
public class FragmentRecycleView extends RecyclerView.Adapter<FragmentRecycleView.MyViewHolder> {


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclesearchfragment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
        //return arrayListDb.size;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtNameDb;
        TextView txtPoinstDb;
        TextView txtImgDb;

        public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                txtNameDb = itemView.findViewById(R.id.db_ini_name);
                txtPoinstDb = itemView.findViewById(R.id.db_ini_points);
                txtImgDb = itemView.findViewById(R.id.db_ini_img);
            }
    }

}