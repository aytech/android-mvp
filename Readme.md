# Example of building an app using the MVP (Model View Presenter) architecture pattern

In MVP, the app consists of the following components:

* **_Presenter_**. Contains presentation logic. No Android specific classes.
* **_Model_**. Contains business logic and data management. No Android specific classes.
* **_View_**. Contains lifecycle methods, view logic and navigation. Extends Activity / Fragment.

## _MVP flow_

1. **View** accepts user input and passes it to **Presenter**.
2. **Presenter** updates **Model**
3. **Presenter** tells **View** to update