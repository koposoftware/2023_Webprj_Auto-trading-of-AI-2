import React, { useEffect, useState } from "react";
import LoadingBox from "./LoadingBox";
import "../styles/TradeHistory2.css";

const TradeHistory2 = () => {
  const [loading, setLoading] = useState(true);
  const [tradehistory, setTradeHistory] = useState([]);
  const [searchOption, setSearchOption] = useState("전체");
  const [searchQuery, setSearchQuery] = useState("");

  const fetchTradeHistory = async (token) => {
    try {
      let response = await fetch("https://api-dev.net/trading/transaction", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token,
        },
        body: JSON.stringify({
          name: searchQuery,
        }),
      });
      if (response.status === 200) {
        let tradeHistoryData = await response.json();

        let transformedTradeHistoryData = tradeHistoryData.map((trade) => ({
          id: trade.code,
          date: new Date(trade.tradeDate).toLocaleString("ko-KR", {
            year: "numeric",
            month: "2-digit",
            day: "2-digit",
            hour: "2-digit",
            minute: "2-digit",
            second: "2-digit",
          }),
          position: trade.amount >= 0 ? "매수" : "매도",
          stockCode: trade.code,
          stockName: trade.name,
          unitPrice: trade.pricePerStock.toLocaleString("ko-KR"),
          quantity: Math.abs(trade.amount).toLocaleString("ko-KR"),
          amount: trade.totalPrice.toLocaleString("ko-KR"),
        }));

        setTradeHistory(transformedTradeHistoryData);
        setLoading(false);
      } else {
        window.location.reload();
      }
    } catch (error) {
      console.error(
        "An error occurred while fetching trade history data:",
        error
      );
    }
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    fetchTradeHistory(token);
  }, [searchQuery]);

  const handleOptionChange = (selectedOption) => {
    setSearchOption(selectedOption);
  };

  const handleSearch = (e) => {
    setSearchQuery(e.target.value);
  };

  const handleSearchSubmit = (e) => {
    e.preventDefault();
    const token = localStorage.getItem("token");
    fetchTradeHistory(token);
  };

  const filteredTradeHistory = tradehistory.filter((trade) => {
    if (searchOption === "전체") {
      return trade.stockName.includes(searchQuery);
    } else {
      return (
        trade.stockName.includes(searchQuery) && trade.position === searchOption
      );
    }
  });

  return (
    <div className="tradehistory-container">
      <div className="tradehistory-inner-container">
        <div className="trade-history">
          <h1 className="trade-history-title">거래내역</h1>
          <div className="search-option">
            <div className="search-date">
              <button className="search-today">당일</button>
              <button className="search-week">1주일</button>
              <button className="search-month">1개월</button>
              <button className="search-3months">3개월</button>
              <button className="search-6months">6개월</button>
              <button className="search-direct-input">직접입력</button>
            </div>
            <div className="search-position">
              <select
                value={searchOption}
                onChange={(e) => handleOptionChange(e.target.value)}
              >
                <option value="전체">전체</option>
                <option value="매도">매도</option>
                <option value="매수">매수</option>
              </select>
            </div>
            <div className="search-stock">
              <form onSubmit={handleSearchSubmit}>
                <input
                  type="text"
                  placeholder="종목명을 입력하세요"
                  value={searchQuery}
                  onChange={handleSearch}
                />
                <button type="submit">검색</button>
              </form>
            </div>
          </div>
          <div className="trade-view">
            <div className="table-container">
              <table className="trade-table">
                <thead>
                  <tr>
                    <th>거래일자</th>
                    <th>포지션</th>
                    <th>종목코드</th>
                    <th>종목명</th>
                    <th>거래단가</th>
                    <th>거래수량</th>
                    <th>거래금액</th>
                  </tr>
                </thead>
                <tbody>
                  {filteredTradeHistory.map((trade) => (
                    <tr key={trade.id}>
                      <td>{trade.date}</td>
                      <td
                        className={
                          trade.position === "매수"
                            ? "buy-position"
                            : "sell-position"
                        }
                      >
                        {trade.position}
                      </td>
                      <td>{trade.stockCode}</td>
                      <td>{trade.stockName}</td>
                      <td>₩{trade.unitPrice}</td>
                      <td>{trade.quantity}</td>
                      <td>₩{trade.amount}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TradeHistory2;
