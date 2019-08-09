# Workforce Optimization
Solution code for workforce optimization 

## Assumptions:
1. Number of rooms not more than 100 in a given structure.
2. The ideal workforce for a Structure with 1 to 10 is 1, then 11-20 is 2 and so on. So the workforce demand increase every 10 rooms.
3. The number of rooms cannot be zero.
4. Assuming adding Spring security is not necessary to the endpoint


## Steps to run code:
1. Import/Open the project folder in IntelliJ/Eclipse IDE.
2. Create a new Run Configuration under Application and select 'MainApplication' as Main Class.
3. Run mvn clean package.
4. Run the application from the Run Menu bar (Run-> Run MainApplication).
5. Once the application is up the api can be accessed from REST client like Postman at e.g
   POST "localhost:8080/api/work-optimizer/v1/optimize".
6. For input { "rooms": [15, 25], "senior": 50, "junior": 40 } the output shall be 
	{"resultList": [{"senior": 1,"junior": 1},{"senior": 2,"junior": 1}]}

	
## Logic:

1. I followed a logic where we first determine the optimal total workforce required for a every structure. For e.g for structure with 81     rooms the ideal optimal workforce is 9 workers. (Based on assumption number #2 above)
2. Once we have the total optimal workforce count then we compare it with total available worforce in our input. If the demand is more than supply we reduce the demand ideally first by half and then try to match the demand to the supply.
3. In a similar way further the optimal Senior workforce required is determined from the total optimal workforce and based on demand and supply the values are adjusted for optimal senior workforce solution.
4. Once we have the optimal count for Senior workforce finnaly the junior workforce demand can be determined and can be adjusted to the actually available value.