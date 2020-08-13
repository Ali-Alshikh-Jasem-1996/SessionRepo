package js.technology.session.ui.details;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import js.technology.session.R;
import js.technology.session.data.model.db.Invitees;
import js.technology.session.databinding.ItemInviteesBinding;

public class AdapterInvitees extends RecyclerView.Adapter<AdapterInvitees.InviteesViewHolder> {

    private List<Invitees> inviteesList = new ArrayList<>();
    private InviteeClickEvent inviteeClickEvent;

    @NonNull
    @Override
    public InviteesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInviteesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_invitees, parent, false);
        return new AdapterInvitees.InviteesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InviteesViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return inviteesList.size();
    }

    Invitees getItemByPosition(int position) {
        return inviteesList.get(position);
    }

    public void setInviteesList(List<Invitees> invitees) {
        inviteesList = invitees;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class InviteesViewHolder extends RecyclerView.ViewHolder {

        private ItemInviteesBinding mItemInviteesBinding;

        InviteesViewHolder(ItemInviteesBinding binding) {
            super(binding.getRoot());
            this.mItemInviteesBinding = binding;
        }

        public void onBind(int position) {
            final Invitees invitees = inviteesList.get(position);
            mItemInviteesBinding.setInvitees(invitees);
            mItemInviteesBinding.setPosition(position);
            mItemInviteesBinding.setClickItem(inviteeClickEvent);
            mItemInviteesBinding.executePendingBindings();
        }
    }

    public void setMyListener(InviteeClickEvent listener) {
        this.inviteeClickEvent = listener;
    }
}
