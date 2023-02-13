import React, { useState, useEffect } from 'react';

import { getLoggers, changeLogLevel } from '../administration.reducer';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const LogsPage = () => {
  const [filter, setFilter] = useState('');
  const logs = useAppSelector(state => state.administration.logs);
  const isFetching = useAppSelector(state => state.administration.loading);
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getLoggers());
  }, []);

  const changeLevel = (loggerName, level) => () => dispatch(changeLogLevel(loggerName, level));

  const changeFilter = evt => setFilter(evt.target.value);

  const getClassName = (level, check, className) => (level === check ? `btn btn-sm btn-${className}` : 'btn btn-sm btn-light');

  const filterFn = l => l.name.toUpperCase().includes(filter.toUpperCase());

  const loggers = logs ? Object.entries(logs.loggers).map((e: any) => ({ name: e[0], level: e[1].effectiveLevel })) : [];

  return (
    <div>
      <h2 id="logs-page-heading" data-cy="logsPageHeading">
        Ghi logs
      </h2>
      <p>Có tổng cộng {loggers.length} điểm ghi logs.</p>

      <span>Lọc</span>
      <input type="text" value={filter} onChange={changeFilter} className="form-control" disabled={isFetching} />

      <table className="table table-sm table-striped table-bordered" aria-describedby="logs-page-heading">
        <thead>
          <tr title="click to order">
            <th>
              <span>Tên</span>
            </th>
            <th>
              <span>Cấp độ</span>
            </th>
          </tr>
        </thead>
        <tbody>
          {loggers.filter(filterFn).map((logger, i) => (
            <tr key={`log-row-${i}`}>
              <td>
                <small>{logger.name}</small>
              </td>
              <td>
                <button
                  disabled={isFetching}
                  onClick={changeLevel(logger.name, 'TRACE')}
                  className={getClassName(logger.level, 'TRACE', 'primary')}
                >
                  TRACE
                </button>
                <button
                  disabled={isFetching}
                  onClick={changeLevel(logger.name, 'DEBUG')}
                  className={getClassName(logger.level, 'DEBUG', 'success')}
                >
                  DEBUG
                </button>
                <button
                  disabled={isFetching}
                  onClick={changeLevel(logger.name, 'INFO')}
                  className={getClassName(logger.level, 'INFO', 'info')}
                >
                  INFO
                </button>
                <button
                  disabled={isFetching}
                  onClick={changeLevel(logger.name, 'WARN')}
                  className={getClassName(logger.level, 'WARN', 'warning')}
                >
                  WARN
                </button>
                <button
                  disabled={isFetching}
                  onClick={changeLevel(logger.name, 'ERROR')}
                  className={getClassName(logger.level, 'ERROR', 'danger')}
                >
                  ERROR
                </button>
                <button
                  disabled={isFetching}
                  onClick={changeLevel(logger.name, 'OFF')}
                  className={getClassName(logger.level, 'OFF', 'secondary')}
                >
                  OFF
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default LogsPage;