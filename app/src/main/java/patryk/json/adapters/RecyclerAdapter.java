package patryk.json.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;

import java.util.List;

import patryk.json.R;
import patryk.json.model.Car;
import patryk.json.model.Insurance;
import patryk.json.model.Part;
import patryk.json.model.Service;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private OnItemClickListener mListener;
    private Context context;
    private List<? extends Car> list;
    private int layoutRes;
    private RecyclerView recyclerView;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public RecyclerAdapter(Context context, List list, int layoutRes) {
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
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {

        Object item = getItem(position);

        if (item instanceof Car) {

            Car car = (Car) item;

            holder.marka.setText(car.getMarka());
            holder.model.setText(car.getModel());
            holder.rokProd.setText(car.getRokProdukcji());
            holder.pojemnosc.setText(car.getPojemnosc());
            holder.moc.setText(car.getMoc());

            Glide.with(context).load(car.getImage()).centerCrop().into(holder.image);

            // Expand / collapse cardView on button click with smooth animation
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.expandableLayout.getVisibility() == View.GONE) {
                        TransitionManager.beginDelayedTransition(recyclerView, new AutoTransition());
                        holder.expandableLayout.setVisibility(View.VISIBLE);
                        holder.button.setImageResource(R.drawable.ic_expand_less_white);
                    } else {
                        TransitionManager.beginDelayedTransition(recyclerView, new AutoTransition());
                        holder.expandableLayout.setVisibility(View.GONE);
                        holder.button.setImageResource(R.drawable.ic_expand_more_white);
                    }
                }
            });

        } else if (item instanceof Part) {

            Part part = (Part) item;

            holder.partName.setText(part.getPartName());
            holder.partAdditionalInfo.setText(part.getAdditionalInfo());
            holder.partReplacementDate.setText(String.format(context.getString(R.string.data_wymiany), part.getDate()));
            holder.partPrice.setText(String.format(context.getString(R.string.cena), part.getPrice()));

        } else if (item instanceof Insurance) {

            Insurance insurance = (Insurance) item;

            holder.docTypeName.setText(context.getString(R.string.ubezpieczenie));
            holder.docInfo.setText(String.format(context.getString(R.string.numer_polisy), insurance.getPolicyNr()));
            holder.docAdditionalInfo.setText(String.format(context.getString(R.string.ubezpieczyciel), insurance.getAdditionalInfo()));
            holder.docDateFrom.setText(String.format(context.getString(R.string.ważne_od), insurance.getDateFrom()));
            holder.docDateTo.setText(String.format(context.getString(R.string.ważne_do), insurance.getDateTo()));

        } else if (item instanceof Service) {

            Service service = (Service) item;

            holder.docTypeName.setText(context.getString(R.string.badanie_techniczne));
            holder.docInfo.setText(String.format(context.getString(R.string.numer_rejestracyjny), service.getRegistryNr()));
            holder.docAdditionalInfo.setText(String.format(context.getString(R.string.przebieg), service.getMileage()));
            holder.docDateFrom.setText(String.format(context.getString(R.string.ważny_od), service.getDateFrom()));
            holder.docDateTo.setText(String.format(context.getString(R.string.ważny_do), service.getDateTo()));

        }

        // Apply animation when the  view is bound
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    // Fill adapter with new list
    public void updateList(List updatedList) {
        this.list = updatedList;
        notifyDataSetChanged();
    }

    private Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView image;
        private ImageButton button;
        private ConstraintLayout expandableLayout;
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
                docTypeName,
                docInfo,
                docAdditionalInfo,
                docDateFrom,
                docDateTo;

        RecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);

            // Car info
            image = itemView.findViewById(R.id.car_image);
            marka = itemView.findViewById(R.id.car_name);
            model = itemView.findViewById(R.id.car_model);
            rokProd = itemView.findViewById(R.id.car_prod_date);
            pojemnosc = itemView.findViewById(R.id.car_engine_capacity);
            moc = itemView.findViewById(R.id.car_power);

            // Parts info
            partName = itemView.findViewById(R.id.part_name);
            partAdditionalInfo = itemView.findViewById(R.id.additional_info);
            partReplacementDate = itemView.findViewById(R.id.part_date);
            partPrice = itemView.findViewById(R.id.part_price);

            // Documents info
            docTypeName = itemView.findViewById(R.id.document_type_label);
            docInfo = itemView.findViewById(R.id.document_info);
            docAdditionalInfo = itemView.findViewById(R.id.document_additional_info);
            docDateFrom = itemView.findViewById(R.id.document_date_from);
            docDateTo = itemView.findViewById(R.id.document_date_to);

            // Expandable layout in Car cardView
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            button = itemView.findViewById(R.id.button_expand);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }
    }
}
