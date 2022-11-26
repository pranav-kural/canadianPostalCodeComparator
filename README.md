# Canadian Postal Code Comparator
A utility program to compare closeness between Canadian Postal Codes. This can be used for sorting objects by postal codes.

## Usage

Use case: 
Sorting objects by closeness to one origin Postal Code. 
Example, sorting result of a search performed by a user looking for closest available taxi or food ordering options.
User's address in this case is the **origin** postal code, and each instance of search result item contains some sort of instance (ex: taxi driver or cafe) which has a postal code (address).

Example:

Client search for closest cafes:

    class Cafe {
      .. attributes ..
      
      Address cafeAddress;

    }

    class Address {
      .. attributes ..
      
      String postalCode;
    }

A class which handles search might look like:

    class SearchCafes {

         .. attributes ..
         // class instance for postal code comparator (not needed if you only want to use static methods)
         PostalCodeComparator postalCodeComparator;
         
         // constructor
         public SearchCafes () {
            // instantiate the postal comparator: providing it the Postal Code of client (you'd have your own logic to retrieve this info)
            this.postalCodeComparator = new PostalCodeComparator(App.getClient().getAddress().getPostalCode());
         }
         
         .. other methods ..

         List<Cafe> getSearchResult(String query) {

            // get an **unsorted** list of cafes matching the search query
            List<Cafe> searchResult = searchCafes(query);

            // sort the result by closeness to the client making the search
            Collections.sort(searchResult, (firstCafe, secondCafe) -> {
              return postalCodeComparator.comparePostalCodes(firstCafe.getAddress().getPostalCode(), secondCafe.getAddress().getPostalCode());
            });

            // return the sorted result
            return searchResult;
         }
    }

You could also implement the Comparator interface in your Address class, or store postal codes in your Address class as an instance of PostalCode, or use Heap-sort instead of Collection sort.

You can have a look at postalCodeCompSample.java to get an example.

## Methodolgy

The Candian postal code is dividing mainly into two parts: FSA and LDU ([more info](https://www.canadapost-postescanada.ca/cpc/en/support/articles/addressing-guidelines/postal-codes.page))

The methodolgy implemented in this utility program, compares every bit of the postal code individually. 

Example: 
- We first compare the Postal District Character of the FSA portion of each of the postal codes. 
- If these bits of all postal codes being compared are the same, then this comparison is inconclusive, and we need to compare second bit of FSA of each postal code
- However, if these bits are different, if Postal District code of first postal code is closer to postal code of origin, we return 1, else we return -1 to indicate second postal code is closer to origin.

The comparion is not **100%** garaunteed to be accurate, since some bits of the postal code can't be directly compared to deduce closeness or distance.

For example, in case of origin: "T0L 1M4", first: "T1K 4L9", second: "T8L 5C3" - first may be deduced to be closer to the origin, but the Urban Rural Identifier (second bit of the postal code) of origin is 0, indicating it is a rural zone, so it is not necessary that first is closer to origin than second (i.e., not sequentially alloted).

Moreover, not all edge cases are handled, example specialized postal codes like "H0H0H0", which don't have a real physical address or location is still considered valid postal code.

## Disclaimer

Important note: 'closeness' here refers to closeness measured using postal codes, and that might not always represent the real physical geographically closeness of an object to another, since, the postal code allocation is not done based on geographical closeness, but based on regional parameters. Example, by postal codes, Toronto is nearer to Ottawa than Montreal, because postal codes of Toronto and Ottawa are more closely related (same region) than Ottawa and Montreal (different provinces), even though in reality Montreal is closer to Ottawa. This comparison based on postal code is however good enough where geo-coordinates aren't available or high degree of accuracy is not needed (because computing geo-coords require additional overhead).
