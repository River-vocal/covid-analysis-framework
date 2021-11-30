Java-based Covid Analysis Framework

Exploration and Analysis of Covid Cases from Different Countries

A black-box framework that reads data from the data  plugin, operates calculation on the data, 
then communicates with display plugin for visualization

0) When you load source for the three plugins, for simplicity, you do not need to give any input. Submit with an empty input would direct to the default source for each corresponding data plugin.

1) This is a framework that analyzes the data for COVID-19. It can read up to ten countries such as the U.S.A., China, Korea, etc. Meanwhile, it can read categories including death number, hospitalized number, and positive cases, as long as the resources contain any of these categories.

2) There are three main data plugins: local json file, and two api trackers. Each data plugin will provide the categories that can be accessible. There are two modes to be stored: a discrete series of increase number for each month (starting from Jan. 2020 to March 2021), and a cumulative number that contains the sum of this entire period.

2.1) To access local json reader: either input "src/main/resources/USdaily.json" for a sample file, or does not provide any input (which will direct to the default file as mentioned previously).
Source citation: https://api.covidtracking.com/v1/us/daily.json

2.2) To access tracker API: does not need any input, it will directly access to the API. Notice that the website shuts down intermittently, so might need to take few tries.
Source citation: https://coronavirus-tracker-api.herokuapp.com/v2/locations?timelines=1

3) There are three main display plugins: xy-plot, pie chart, and histogram. Each supports different operation method. There are four operations. First, derivation: it would get the change of rate at each point comparing to the previous point. Second, difference: it would get the difference between the current value and the previous. Third, percentage: it will show the percentage of current value comparing to the entire series. Last, origin: it will show the origin series without any operation.
Source citation: https://covid19.who.int/WHO-COVID-19-global-data.csv

3.1) XY-plot can show country/countries with data during the period for each month, as well as cumulative number to compare for different countries.

3.2) Pie chart can show the percentage of the data comparing to the given y-values.

3.3) Histogram can show the distribution of the given series. If the number of x-values is larger than 5, it will always divide into five chunks evenly to analyze the disttribution. If there is less than 5 x-values, it will divided to that specific number of chunks.

4) First, it is important to pick a data plugin that you want to use, along with a resources route if it is needed. Then you can load the resources by cliking the button.

5) After loading the resource, you will be able to pick the category, number of countries, and if you want discrete mode or cumulative numbers. Notice that only valid category would be provided. And if the number of countries is larger than the number in the sources, it would not do anything and will give you a warning message at the bottom. Lastly, it you only pick one country and choose cumulative mode, it would be meaningless. So it will automatically handle as discrete mode when it displays the plot. You have to choose the display plugin before displaying the plot.

6) Then you can choose any operation you want. Only valid operation for this display plugin would be provided. Notice that you can switch back to origin mode whenever you want.

7) If you would like to switch to a different category/source/mode/country number, you need to click display again. Now the new plot would still default to origin and you can pick the operation you need again.

8) The bottom red messages are helpful hints for the process of your operation, especially if you are trying to do some illegal clicks. Make sure always check with that.
