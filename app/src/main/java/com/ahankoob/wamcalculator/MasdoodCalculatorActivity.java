package com.ahankoob.wamcalculator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ahankoob.wamcalculator.tools.MyBounceInterpolator;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;

public class MasdoodCalculatorActivity extends AppCompatActivity {
	TextView MasdoodiActivityTitle,MasdoodiResultAghsatIcon,MasdoodiResultAghsatTitle,MasdoodiResultAghsatText
			,MasdoodiResultSoodIcon,MasdoodiResultSoofTitle,MasdoodiResultSoodText,MasdoodiResultbazpardakhtIcon,MasdoodiResultbazpardakhtTitle,MasdoodiRealDarsadIcon,MasdoodiRealDarsadTitle,MasdoodiResultbazpardakhtText,MasdoodiRealDarsadText;
	EditText MasdoodimablaghVam,MasdoodidarsadSood,Masdoodibazpardakht,MasdoodiMablagh;
	ImageButton MasdoodicalcMohasebe,MasdoodicalcBazgasht,MasdoodicalcReset;
	RelativeLayout MasdoodiFormLayout,MasdoodiResultLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_masdood_calculator);
		PreSetVars();
		setfonts();
		MasdoodimablaghVam.addTextChangedListener(new NumberTextWatcherForThousand(MasdoodimablaghVam));
		MasdoodiMablagh.addTextChangedListener(new NumberTextWatcherForThousand(MasdoodiMablagh));

		MasdoodicalcMohasebe.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
				/* Button Animation */
				final Animation myAnim = AnimationUtils.loadAnimation(MasdoodCalculatorActivity.this, R.anim.bounce);
				MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
				myAnim.setInterpolator(interpolator);
				MasdoodicalcMohasebe.startAnimation(myAnim);

				/* Calculation */
				boolean isValid = true;
				if(MasdoodimablaghVam.getText().toString().trim().length()==0)
				{
					MasdoodimablaghVam.setError(getResources().getString(R.string.EmptyFieldError));
					isValid = false;
				}
				if(MasdoodidarsadSood.getText().toString().trim().length()==0)
				{
					MasdoodidarsadSood.setError(getResources().getString(R.string.EmptyFieldError));
					isValid = false;
				}
				if(Masdoodibazpardakht.getText().toString().trim().length()==0)
				{
					Masdoodibazpardakht.setError(getResources().getString(R.string.EmptyFieldError));
					isValid = false;
				}
				if(MasdoodiMablagh.getText().toString().trim().length()==0)
				{
					MasdoodiMablagh.setError(getResources().getString(R.string.EmptyFieldError));
					isValid = false;
				}
				if(isValid) {

					Double sumVamAmount = Double.valueOf(NumberTextWatcherForThousand.trimCommaOfString(MasdoodimablaghVam.getText().toString().trim()));

					Double darsadSoodAmount = Double.valueOf(MasdoodidarsadSood.getText().toString().trim());

					Double bazpardakhtAmount = Double.valueOf(Masdoodibazpardakht.getText().toString().trim());

					Double masdoodiAmount = Double.valueOf(NumberTextWatcherForThousand.trimCommaOfString(MasdoodiMablagh.getText().toString().trim()));

					Double soorat = 0.0;
					Double temp1 = darsadSoodAmount / 1200;
					soorat += Math.pow((temp1 + 1), bazpardakhtAmount);

					soorat *= temp1;
					soorat *= sumVamAmount;

					Double makhraj = 0.0;

					makhraj += Math.pow((temp1 + 1), bazpardakhtAmount);
					makhraj -= 1;
					Double Ghest = (soorat / makhraj);

					Double temp2 = 0.0;

					temp2 += Ghest * bazpardakhtAmount;
					temp2 -= sumVamAmount;

					Double temp3 = temp2 + sumVamAmount;

					Double newSood = GetRealSood(sumVamAmount,masdoodiAmount,bazpardakhtAmount,darsadSoodAmount,temp3);
					MasdoodiResultAghsatText.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(Integer.valueOf(Ghest.intValue()))) + " ریال");

					MasdoodiResultSoodText.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(Integer.valueOf(temp2.intValue()))) + " ریال");


					MasdoodiResultbazpardakhtText.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(Integer.valueOf(temp3.intValue()))) + " ریال");

					MasdoodiRealDarsadText.setText(String.valueOf(newSood.intValue())+"%");
					/* Result Animation */
					final ObjectAnimator oa1 = ObjectAnimator.ofFloat(MasdoodiFormLayout, "scaleX", 1f, 0f);
					final ObjectAnimator oa2 = ObjectAnimator.ofFloat(MasdoodiResultLayout, "scaleX", 0f, 1f);
					oa1.setInterpolator(new DecelerateInterpolator());
					oa2.setInterpolator(new AccelerateDecelerateInterpolator());
					oa1.addListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							super.onAnimationEnd(animation);
							MasdoodicalcMohasebe.setVisibility(View.GONE);
							MasdoodicalcReset.setVisibility(View.VISIBLE);
							MasdoodiFormLayout.setVisibility(View.GONE);
							MasdoodiResultLayout.setVisibility(View.VISIBLE);

							oa2.start();
						}
					});
					oa1.start();

				}



			}
		});
		MasdoodicalcReset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final ObjectAnimator oa1 = ObjectAnimator.ofFloat(MasdoodiResultLayout, "scaleX", 1f, 0f);
				final ObjectAnimator oa2 = ObjectAnimator.ofFloat(MasdoodiFormLayout, "scaleX", 0f, 1f);
				oa1.setInterpolator(new DecelerateInterpolator());
				oa2.setInterpolator(new AccelerateDecelerateInterpolator());
				oa1.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						super.onAnimationEnd(animation);
						MasdoodimablaghVam.setText("");
						MasdoodidarsadSood.setText("");
						Masdoodibazpardakht.setText("");
						MasdoodiMablagh.setText("");
						MasdoodicalcReset.setVisibility(View.GONE);
						MasdoodicalcMohasebe.setVisibility(View.VISIBLE);
						MasdoodiResultLayout.setVisibility(View.GONE);
						MasdoodiFormLayout.setVisibility(View.VISIBLE);

						oa2.start();
					}
				});
				oa1.start();
			}
		});
		MasdoodicalcBazgasht.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MasdoodicalcBazgasht.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								final Animation myAnim = AnimationUtils.loadAnimation(MasdoodCalculatorActivity.this, R.anim.bounce);
								MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
								myAnim.setInterpolator(interpolator);
								MasdoodicalcBazgasht.startAnimation(myAnim);
								//startActivity(new Intent(CalculatorActivity.this,MainActivity.class));
								finish();
							}
						}, 350);


					}
				});
			}
		});
	}
	public void PreSetVars(){

		MasdoodiResultAghsatIcon = (TextView)findViewById(R.id.MasdoodiResultAghsatIcon);
		MasdoodiResultAghsatTitle = (TextView)findViewById(R.id.MasdoodiResultAghsatTitle);
		MasdoodiResultAghsatText = (TextView)findViewById(R.id.MasdoodiResultAghsatText);

		MasdoodiResultSoodIcon = (TextView)findViewById(R.id.MasdoodiResultSoodIcon);
		MasdoodiResultSoofTitle = (TextView)findViewById(R.id.MasdoodiResultSoofTitle);
		MasdoodiResultSoodText = (TextView)findViewById(R.id.MasdoodiResultSoodText);

		MasdoodiResultbazpardakhtIcon = (TextView)findViewById(R.id.MasdoodiResultbazpardakhtIcon);
		MasdoodiResultbazpardakhtTitle = (TextView)findViewById(R.id.MasdoodiResultbazpardakhtTitle);
		MasdoodiResultbazpardakhtText = (TextView)findViewById(R.id.MasdoodiResultbazpardakhtText);

		MasdoodiRealDarsadIcon = (TextView) findViewById(R.id.MasdoodiRealDarsadIcon);
		MasdoodiRealDarsadText = (TextView) findViewById(R.id.MasdoodiRealDarsadText);
		MasdoodiRealDarsadTitle = (TextView) findViewById(R.id.MasdoodiRealDarsadTitle);

		MasdoodimablaghVam = (TextInputEditText) findViewById(R.id.MasdoodimablaghVam);
		MasdoodidarsadSood = (TextInputEditText) findViewById(R.id.MasdoodidarsadSood);
		Masdoodibazpardakht = (TextInputEditText) findViewById(R.id.Masdoodibazpardakht);
		MasdoodiMablagh = (TextInputEditText) findViewById(R.id.MasdoodiMablagh);



		MasdoodicalcMohasebe = (ImageButton) findViewById(R.id.MasdoodicalcMohasebe);
		MasdoodicalcBazgasht = (ImageButton) findViewById(R.id.MasdoodicalcBazgasht);
		MasdoodicalcReset = (ImageButton) findViewById(R.id.MasdoodicalcReset);

		MasdoodiFormLayout = (RelativeLayout) findViewById(R.id.MasdoodiFormLayout);
		MasdoodiResultLayout = (RelativeLayout) findViewById(R.id.MasdoodiResultLayout);


	}
	public void setfonts(){
		FontManager.markAsIconContainer(MasdoodiActivityTitle, FontManager.getVazirFont(getAssets()));

		FontManager.markAsIconContainer(MasdoodiResultAghsatIcon, FontManager.getfontawesomeFont(getAssets()));
		FontManager.markAsIconContainer(MasdoodiResultSoodIcon, FontManager.getfontawesomeFont(getAssets()));
		FontManager.markAsIconContainer(MasdoodiResultbazpardakhtIcon, FontManager.getfontawesomeFont(getAssets()));
		FontManager.markAsIconContainer(MasdoodiRealDarsadIcon, FontManager.getfontawesomeFont(getAssets()));

		FontManager.markAsIconContainer(MasdoodiResultAghsatTitle, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(MasdoodiResultSoofTitle, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(MasdoodiResultbazpardakhtTitle, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(MasdoodiRealDarsadTitle, FontManager.getVazirFont(getAssets()));

		FontManager.markAsIconContainer(MasdoodiResultAghsatText, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(MasdoodiResultSoodText, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(MasdoodiResultbazpardakhtText, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(MasdoodiRealDarsadText, FontManager.getVazirFont(getAssets()));

		FontManager.markAsIconContainer(MasdoodimablaghVam, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(MasdoodidarsadSood, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(Masdoodibazpardakht, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(MasdoodiMablagh, FontManager.getVazirFont(getAssets()));

		FontManager.markAsIconContainer(MasdoodicalcMohasebe, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(MasdoodicalcBazgasht, FontManager.getVazirFont(getAssets()));



	}
	public Double GetRealSood(Double Asl,Double Masdoodi, Double mah, Double Oldsood,Double Kol){
		Double newSood = Oldsood+0.1;
		Double newKol = 0.0;
		//Kol-=Masdoodi;
		Double sumVamAmount = Asl-Masdoodi;


		Double bazpardakhtAmount = mah;
		while(newKol<Kol){


			Double darsadSoodAmount = newSood;

			Double soorat = 0.0;
			Double temp1 = darsadSoodAmount / 1200;
			soorat += Math.pow((temp1 + 1), bazpardakhtAmount);

			soorat *= temp1;
			soorat *= sumVamAmount;


			Double makhraj = 0.0;

			makhraj += Math.pow((temp1 + 1), bazpardakhtAmount);
			makhraj -= 1;
			Double Ghest = (soorat / makhraj);

			Double temp2 = 0.0;

			temp2 += Ghest * bazpardakhtAmount;
			temp2 -= sumVamAmount;

			newKol = temp2 + sumVamAmount;
			newSood+=0.1;
		}

		return newSood-0.1;
	}

}
