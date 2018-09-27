show price of axp
show price of aapl
show price of ba
show shares of axp
show shares of aapl
show shares of ba
buy 20 shares axp
show shares of axp
sell 30 shares axp
show shares of axp
when aapl under 300 then {
    buy 50 shares aapl
    show shares of aapl
}
when ba over 300 then {
    def sharesOfBA = total shares of ba
    sell (sharesOfBA / 2) shares ba
    show shares of ba
    show value of ba
}
