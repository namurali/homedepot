/**
* This class provides extended functionality of the DefaultCustomerService.
* Design Pattern used: Decorator Pattern.
*
* @author  Murali Narayana
* @version 1.0
* @since   2017-06-30
*/

package murali.narayana.exercise;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.ArrayList;
import de.hybris.platform.servicelayer.internal.service.AbstractBusinessService;

import de.hybris.platform.core.model.product.ProductModel;

import de.hybris.platform.core.model.user.UserModel;


public class DecoratedCustomerReviewService extends AbstractBusinessService {
	
	// The concrete class thats being decorated.
	private DefaultCustomerReviewService concreteCustomerReviewService;

	// The source containing the curse words, which will be used to populate the list containing curse strings..
	private Resource resCurseWordsFilePath;
	
	// The list that will be populated from the file.
	private List<String> listOfCurses = null;
	
	
	@Required
	public void setCustomerReviewDao(DefaultCustomerReviewService customerReviewService) {
		this.concreteCustomerReviewService = customerReviewService;
	}

	@Required
	public void setResCurseWordsFilePath (Resource resCurseWordsFilePath) {
		this.resCurseWordsFilePath = resCurseWordsFilePath;
	}
	
   /**
   * This method is used to get total number of reviews in a given range  (inclusive).
   * @param product the product whose reviews count is to be retrieved.
   * @param frmRange  The double value representing the from range.
   * @param toRange  The double value representing the to range.
   * @return int the total number of reviews for the product in the given range.
   */
	public int getReviewCountForProductInRatingRange(ProductModel product, double frmRange, double toRange) {
		int count = 0;
		
		for (CustomerReviewModel reviewItem: concreteCustomerReviewService.getReviewsForProduct(product)) {
			double reviewItemRating = reviewItem.getRating();
			if (Double.compare (reviewItemRating,frmRange ) >= 0 && Double.compare (reviewItemRating, toRange) <= 0 ) {
				count = count + 1;
			}
		}
		return count;
	}

	/**
	* comments for CustomerReviewModel ...
	*/
	public CustomerReviewModel createCustomerReview (Double rating, String headline, String comment, UserModel user, ProductModel product) throws Exception
	{

		if (listOfCurses == null) {
			populateListOfCurses();
		}
		double lowerLimit = 0;
		if (Double.compare(rating, lowerLimit ) < 0) {
			throw new Exception ("Rating less than 0 is not permitted.");
		}
		for (String curse: listOfCurses) {
			if (comment.toUpperCase().contains(curse.toUpperCase()) {
				throw Exception ("Comment contain curse word.");
			}
		}
		
		return concreteCustomerReviewService(rating, headline, comment, user, product);
	}

	/**
	* Method to populate the list with the curse words.
	* Lazy initialized.
	*/
	private void populateListOfCurses() {
		synchronized(this) {
			if (listOfCurses == null) {
				String pathToFileWithCurseWords = "de/hybris/platform/customerreview/properties/ForbiddenWords.txt";
				//ToDO
				try {
					listOfCurses = new ArrayList<String>();
					Scanner in = new Scanner(resCurseWordsFilePath.getFile());
					while (in.hasNextLine()){
						listOfCurses.add(in.nextLine());
					}
				} catch (Exception e) {
					throw Exception ("Loading curse words failed.");
				}
			}

		}
	}


 }
