package cis350_project_euchre;

/**********************************************************************
 * This enum specifies codes that can be used in the botPlay function
 * in order to play a card or as a return to tell the controller what
 * happened.
 * 
 * @author Michael Fink, Charlie Dorn
 *********************************************************************/
public enum BOTCODE {
	/**
	 * Tells the model that a bot should play a card
	 */
	PLAY,
	
	/**
	 * Tells the model that a bot should swap a card with the kitty
	 */
	SWAP,
	
	/**
	 * Tells the model that a bot should select a suit for trump
	 */
	TRUMP,
	
	/**
	 * Tells the model that a bot should order the kitty as trump
	 */
	HITKITTY,
	
	/**
	 * Tells the controller that the bot selected trump
	 */
	TRUMP_SELECTED,
	
	/**
	 * Tells the controller that the bot did not select trump
	 */
	TRUMP_NOTSELECTED,
	
	/**
	 * Tells the controller if a trick has been played after bot play
	 */
	PLAY_TRICKFINISHED,
	
	/**
	 * Tells the controller if a trick hasn't completed after bot play
	 */
	PLAY_TRICKNOTFINISHED,
	
	/**
	 * Tells the controller a bot swapped their card with the kitty
	 */
	SWAP_SUCCESSFUL,
	
	/**
	 * Tells the controller a bot ordered the kitty as trump
	 */
	HITKITTY_HIT,
	
	/**
	 * Tells the controller a bot did not order the kitty as trump
	 */
	HITKITTY_NOHIT,
	
	/**
	 * Default value, means nothing and shouldn't ever be used, but
	 * is needed as a default return in the botPlay function
	 */
	DEFAULT
}
