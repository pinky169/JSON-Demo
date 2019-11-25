package patryk.json.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import patryk.json.R;
import patryk.json.model.Car;
import patryk.json.model.Insurance;
import patryk.json.model.Part;
import patryk.json.model.Service;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    public OnItemClickListener mListener;
    private Context context;
    private List<?> list;
    private int layoutRes;

    public RecyclerAdapter(Context context, List<?> list, int layoutRes) {
        this.context = context;
        this.list = list;
        this.layoutRes = layoutRes;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutRes, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        Object object = list.get(position);

        if (object instanceof Car) {

            Car car = (Car) object;

            holder.marka.setText(car.getMarka());
            holder.model.setText(car.getModel());
            holder.rokProd.setText(car.getRokProdukcji());
            holder.pojemnosc.setText(car.getPojemnosc());
            holder.moc.setText(car.getMoc());

            Glide.with(context).load(car.getImage()).fitCenter().into(holder.image);

        } else if (object instanceof Part) {

            Part part = (Part) object;

            holder.partName.setText(part.getPartName());
            holder.partAdditionalInfo.setText(part.getAdditionalInfo());
            holder.partReplacementDate.setText(part.getDate());
            holder.partPrice.setText(part.getPrice());

        } else if (object instanceof Insurance) {

            Insurance insurance = (Insurance) object;

            holder.docInfo.setText(insurance.getPolicyNr());
            holder.docAdditionalInfo.setText(insurance.getAdditionalInfo());
            holder.docDateFrom.setText(insurance.getDateFrom());
            holder.docDateTo.setText(insurance.getDateTo());

        } else if (object instanceof Service) {

            Service service = (Service) object;

            holder.docInfo.setText(service.getRegistryNr());
            holder.docAdditionalInfo.setText(service.getMileage());
            holder.docDateFrom.setText(service.getDateFrom());
            holder.docDateTo.setText(service.getDateTo());

        }
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        //private MaterialButton button;
        private TextView
                marka,
                model,
                rokProd,
                pojemnosc,
                moc,
                partName,
                partAdditionalInfo,
                partReplacementDate,
                partPrice,
                docInfo,
                docAdditionalInfo,
                docDateFrom,
                docDateTo;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.car_image);
            marka = itemView.findViewById(R.id.car_name);
            model = itemView.findViewById(R.id.car_model);
            rokProd = itemView.findViewById(R.id.car_prod_date);
            pojemnosc = itemView.findViewById(R.id.car_engine_capacity);
            moc = itemView.findViewById(R.id.car_power);

            partName = itemView.findViewById(R.id.part_name);
            partAdditionalInfo = itemView.findViewById(R.id.additional_info);
            partReplacementDate = itemView.findViewById(R.id.part_date);
            partPrice = itemView.findViewById(R.id.part_price);

            docInfo = itemView.findViewById(R.id.document_info);
            docAdditionalInfo = itemView.findViewById(R.id.document_additional_info);
            docDateFrom = itemView.findViewById(R.id.document_date_from);
            docDateTo = itemView.findViewById(R.id.document_date_to);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
