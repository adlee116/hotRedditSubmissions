Have hit the 5+ hour mark so am forcing myself to stop.   What I have tried to achieve here, is to fully implement the request application using clean code architecture with SOLID principles.

I have not yet added tests to this project but wanted to ensure the product was working and have now run out of time. With the design of the code the testing would be straight forward.

I have not done any local caching. My mindset would be to persist each item down to the database at the point that we convert these in to ‘usable models’. This would then allow the fetch for the list to be collected from the DB. This would also mean that I could better handle state in the UI in regard to the android life cycle. We would want to ensure that rotation etc, would not destroy the list and require another fetch.

There are cases here that I would group things such as styles, to ensure reusability, reducing time in spent in the future.

Util to be built, so that int conversion can be tested.

I have tried to stick to the basic UI / Colours that are used by Reddit, to ensure transition between the apps are user friendly.   I have had to complete this project using InteliJ, although the task did stipulate Android Studio. This due to using the new M1 chip MacBook Pro.  Android studio is not yet optimised to run on these chips which makes it incredibly slow and sometimes unresponsive.

Emulator Chrome does not work, so will be testing that clicking one of the reddit posts correctly navigates the user to chrome. This note is in case I forget.   Appreciate the opportunity, enjoyed the challenge and would love honest feedback, regardless of it being negative or positive. As always, I believe that every experience can be used to improve.

Aidan Lee

