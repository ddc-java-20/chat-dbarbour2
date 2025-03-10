package edu.cnm.deepdive.chat.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.chat.R;
import edu.cnm.deepdive.chat.viewmodel.LoginViewModel;

/** @noinspection deprecation*/
@AndroidEntryPoint
public class PreLoginFragment extends Fragment {
private View root;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    root = inflater.inflate(R.layout.fragment_pre_login,container,false);
    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    LoginViewModel viewModel = new ViewModelProvider(requireActivity())
        .get(LoginViewModel.class);

    LifecycleOwner owner = getViewLifecycleOwner();

    //observe the silent google login event to see if user
    //successfully logs in (in the backround)
    viewModel
        .getAccount()
        .observe(owner, this::handleAccount);

    //observe the silent google login.  If an error is returned, the user
    //isn't logged in, so you need to direct them to the google login screen
    viewModel
        .getRefreshThowable()
        .observe(owner, this::handleThrowable);

    //start the Google silent login
    viewModel.refresh();

  }

  private void handleThrowable(Throwable refreshThowable) {
    if(refreshThowable != null) {
      Navigation.findNavController(root)
          .navigate(PreLoginFragmentDirections.navigateToLoginFragment());
    }
  }

  private void handleAccount(GoogleSignInAccount account) {
    if(account != null) {
      Navigation.findNavController(root)
          .navigate(PreLoginFragmentDirections.navigateToHomeFragment());
    }
  }

}
