package com.chotix.mydiary1.entries.photo;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chotix.mydiary1.R;
//import com.facebook.samples.zoomable.ZoomableDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PhotoDetailViewerFragment extends Fragment {

    @BindView(R.id.zdv_photo_detail)
//    public ZoomableDraweeView zdvPhotoDetail;
    public Unbinder unbinder;

    private Uri photoUri;

    public static PhotoDetailViewerFragment newInstance(Uri photoUri) {
        Bundle args = new Bundle();
        PhotoDetailViewerFragment fragment = new PhotoDetailViewerFragment();
        args.putParcelable("photoUri", photoUri);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary_photo_detail_viewer, container, false);
        unbinder = ButterKnife.bind(this, view);
        photoUri = getArguments().getParcelable("photoUri");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initZoomableDraweeView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initZoomableDraweeView() {
//        zdvPhotoDetail.setAllowTouchInterceptionWhileZoomed(true);
//        // needed for double tap to zoom
//        zdvPhotoDetail.setIsLongpressEnabled(false);

        //Implement the InmmersiveMode
        //TODO:
//        zdvPhotoDetail.setTapListener(
//                new TapGestureListener(getActivity().getWindow().getDecorView(), zdvPhotoDetail));
//
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setUri(photoUri)
//                .build();
//        zdvPhotoDetail.setController(controller);
    }
}
