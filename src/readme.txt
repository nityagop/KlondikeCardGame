For HW3 I made the following changes to my BasicKlondike implementation:
- Removed an exception being thrown in movePile related to there being not
enough cards to move
- fixed my moveDrawToFoundation method to remove the item from the draw pile
(it was adding it to the foundation pile and the draw pile before)
- added more descriptive java docs to make my code more readable code
- fixed the sameCard method in my PlayingCard class to include the visible
instance variable when checking for sameness between two cards
- start game


For HW4 I made the following changes to my TextualController:
- I fixed the way my controller was outputting invalid messages, I was not adding a new line

For HW4 I made the following changes to my BasicKlondike:
- Fixed my moveDraw method because it was not removing the card that was being moved from
the draw pile
- Removed an exception being thrown in movePile because it was throwing unecessary exceptions
- Added to my startGame method so that it throws an exception if startGame is called
when the game is already running
- Fixed my discardDraw because it was not adding the card to the end of the list
